package com.learn.robot.service.impl;

import com.learn.robot.Exception.RobotException;
import com.learn.robot.Exception.ServiceException;
import com.learn.robot.dao.LoginUserMapperExt;
import com.learn.robot.domain.LoginUser;
import com.learn.robot.enums.ServiceExceptionEnum;
import com.learn.robot.mapper.LoginUserMapper;
import com.learn.robot.service.LoginService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginUserMapperExt loginUserMapperExt;

    @Autowired
    LoginUserMapper loginUserMapper;

    @Override
    public  List<LoginUser> getUserList(){
        List<LoginUser> userList= loginUserMapperExt.getUserList();
        if (CollectionUtils.isNotEmpty(userList)) {
            return userList;
        }
        return new ArrayList<>();
    }

    @Override
    public LoginUser getUserById(String id) throws ServiceException {
        if("2".equals(id)){
            throw RobotException.serviceException(ServiceExceptionEnum.LACK_PARAMS);
        }
        return loginUserMapper.selectByIds(id).get(0);
    }


}
