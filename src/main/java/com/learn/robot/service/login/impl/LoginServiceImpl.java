package com.learn.robot.service.login.impl;

import com.learn.robot.exception.RobotException;
import com.learn.robot.exception.ServiceException;
import com.learn.robot.domain.User;
import com.learn.robot.enums.ServiceExceptionEnum;
import com.learn.robot.mapper.LoginMapper;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@Scope("singleton")
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginMapper loginMapper;

    @Autowired
    CommonService commonService;

    /**
     * 登录获取使用人的基本信息
     */
    @SuppressWarnings("unused")
    @Override
    public User loginBusinessHall(String username, String pwd) throws ServiceException, Exception {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(pwd)) {
            throw RobotException.serviceException(ServiceExceptionEnum.ILLEGAL_PARAM);
        }
        List<User> userList = loginMapper.getUserByUserName(username);
        if (CollectionUtils.isNotEmpty(userList)) {
            User user = userList.get(0);
            log.info("loginBusinessHall | User info = {}", user);
            dealUserLock(user);
            //校验输入的密码和数据库的密码是否一致
            String decrypt_pwd = pwd;
            pwd = Md5Encrypt.md5(pwd);
            if (StringUtils.isNotBlank(user.getPwd()) && pwd.equalsIgnoreCase(user.getPwd())) {
                log.info("loginBusinessHall | Pwd is same. pwd 1 = {}, pwd 2 = {}", pwd, user.getPwd());
                dealPwdSuccess(user);
            } else {
                log.info("loginBusinessHall | Pwd is error. pwd 1 = {}, pwd 2 = {}", pwd, user.getPwd());
                dealPwdError(user);
            }
            CheckPWDUtil.EvalPWD(decrypt_pwd, username);
            return user;
        } else {
            throw RobotException.serviceException(ServiceExceptionEnum.LOGIN_PWD_ERROR);
        }
    }

    @Override
    public User getLoginUser() throws ServiceException, Exception {
        HttpSession session = commonService.getSession();
        User user;
        user = (User) session.getAttribute("User");
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


    public void dealUserLock(User user) {
        String lockType = StringUtils.isNotBlank(user.getLock_type()) ? user.getLock_type() : "0";
        switch (lockType) {
            case "1":
                throw RobotException.serviceException(ServiceExceptionEnum.USER_IS_LOCK);
            case "2":
                throw RobotException.serviceException(ServiceExceptionEnum.USER_IS_COLD);
            default:
                break;
        }
    }

    public void dealPwdSuccess(User user) {
        log.info("===========> 重置用戶登錄次數為0");
        loginMapper.updateLoginattempsAndLock("0", "0", user.getUser_no());
    }


    public void dealPwdError(User user) {
        String loginattemps = StringUtils.isNotBlank(user.getLoginattemps()) ? user.getLoginattemps() : "0";
        if (Integer.valueOf(loginattemps) + 1 <= 2) {// 更新错误次数
            loginMapper.updateLoginattempsAndLock(String.valueOf(Integer.valueOf(loginattemps) + 1), "0", user.getUser_no());
        } else {// 锁住用户
            loginMapper.updateLoginattempsAndLock(String.valueOf(Integer.valueOf(loginattemps) + 1), "1", user.getUser_no());
        }
        throw RobotException.serviceException(ServiceExceptionEnum.PWD_ERROR);
    }

}
