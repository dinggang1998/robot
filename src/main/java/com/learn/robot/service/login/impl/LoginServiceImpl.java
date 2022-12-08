package com.learn.robot.service.login.impl;

import com.learn.robot.exception.RobotException;
import com.learn.robot.exception.ServiceException;
import com.learn.robot.enums.ServiceExceptionEnum;
import com.learn.robot.mapper.DzUserMapper;
import com.learn.robot.model.user.DzUser;
import com.learn.robot.service.common.CommonService;
import com.learn.robot.service.login.LoginService;
import com.learn.robot.util.CheckPWDUtil;
import com.learn.robot.util.Md5Encrypt;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;


@Slf4j
@Service
@Scope("singleton")
public class LoginServiceImpl implements LoginService {

    @Autowired
    CommonService commonService;

    @Autowired
    DzUserMapper dzUserMapper;

    /**
     * 登录获取使用人的基本信息
     */
    @SuppressWarnings("unused")
    @Override
    public DzUser loginWeb(String username, String pwd) throws ServiceException, Exception {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(pwd)) {
            throw RobotException.serviceException(ServiceExceptionEnum.ILLEGAL_PARAM);
        }
        Example example = new Example(DzUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        List<DzUser> userList=dzUserMapper.selectByExample(example);

        if (CollectionUtils.isNotEmpty(userList)) {
            DzUser user = userList.get(0);
            log.info("User info = {}", user);
            //判断用户是否冻结或锁定
            dealUserLock(user);
            //判断用户名密码是否合规
            CheckPWDUtil.EvalPWD(pwd, username);
            //密码是否与数据库一致
            pwd = Md5Encrypt.md5(pwd);
            if (pwd.equalsIgnoreCase(user.getPassword())) {
                log.info("Pwd is same. pwd 1 = {}, pwd 2 = {}", pwd, user.getPassword());
                dealPwdSuccess(user);
            } else {
                log.info("Pwd is error. pwd 1 = {}, pwd 2 = {}", pwd, user.getPassword());
                dealPwdError(user);
            }
            return user;
        } else {
            throw RobotException.serviceException(ServiceExceptionEnum.NO_USER);
        }
    }

    @Override
    public DzUser getLoginUser() throws ServiceException, Exception {
        HttpSession session = commonService.getSession();
        DzUser user;
        user = (DzUser) session.getAttribute("User");
        if (user == null) {
            throw RobotException.serviceException(ServiceExceptionEnum.USER_NO_LOGIN);
        }
        return user;
    }

    @Override
    public void reGenerateSessionId(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        // 首先将原session中的数据转移至一临时map中
        Map<String, Object> tempMap = new HashMap<String, Object>();
        Enumeration<String> sessionNames = session.getAttributeNames();
        while (sessionNames.hasMoreElements()) {
            String sessionName = sessionNames.nextElement();
            tempMap.put(sessionName, session.getAttribute(sessionName));
        }
        // 注销原session，为的是重置sessionId
        session.invalidate();
        // 将临时map中的数据转移至新session
        session = request.getSession();
        for (Map.Entry<String, Object> entry : tempMap.entrySet()) {
            session.setAttribute(entry.getKey(), entry.getValue());
        }
    }


    public void dealUserLock(DzUser user) {
        String lockType = StringUtils.isNotBlank(user.getLockType()) ? user.getLockType() : "0";
        switch (lockType) {
            case "1":
                throw RobotException.serviceException(ServiceExceptionEnum.USER_IS_LOCK);
            case "2":
                throw RobotException.serviceException(ServiceExceptionEnum.USER_IS_COLD);
            default:
                break;
        }
    }

    public void dealPwdSuccess(DzUser user) {
        updateLoginattempsAndLock("0", "0", user.getUserNo());
    }


    public void dealPwdError(DzUser user) {
        String loginattemps = StringUtils.isNotBlank(user.getLoginattemps()) ? user.getLoginattemps() : "0";
        if (Integer.valueOf(loginattemps) + 1 <= 2) {// 更新错误次数
            updateLoginattempsAndLock(String.valueOf(Integer.valueOf(loginattemps) + 1), "0", user.getUserNo());
        } else {// 锁住用户
            updateLoginattempsAndLock(String.valueOf(Integer.valueOf(loginattemps) + 1), "1", user.getUserNo());
        }
        throw RobotException.serviceException(ServiceExceptionEnum.PWD_ERROR);
    }

    public void updateLoginattempsAndLock(String loginattemps, String lockType, String userNo){
        Example example = new Example(DzUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userNo", userNo);

        DzUser dzUser=new DzUser();
        dzUser.setLoginattemps(loginattemps);
        dzUser.setLockType(lockType);
        dzUser.setLoginTime(new Date());
        dzUserMapper.updateByExampleSelective(dzUser,example);
    }

}
