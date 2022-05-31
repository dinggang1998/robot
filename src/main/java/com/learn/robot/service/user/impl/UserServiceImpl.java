package com.learn.robot.service.user.impl;

import com.learn.robot.Exception.RobotException;
import com.learn.robot.Exception.ServiceException;
import com.learn.robot.dao.UserMapperExt;
import com.learn.robot.domain.LoginUser;
import com.learn.robot.enums.ServiceExceptionEnum;
import com.learn.robot.mapper.UserMapper;
import com.learn.robot.service.user.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapperExt userMapperExt;

    @Autowired
    UserMapper userMapper;

    @Override
    public  List<LoginUser> getUserList(){
        List<LoginUser> userList= userMapperExt.getUserList();
        if (CollectionUtils.isNotEmpty(userList)) {
            return userList;
        }
        return new ArrayList<>();
    }

    @Override
    public LoginUser getUserById(String id) throws ServiceException {
        if(StringUtils.isBlank(id)){
            throw RobotException.serviceException(ServiceExceptionEnum.LACK_PARAMS);
        }
        List<LoginUser> userList= userMapper.selectByIds(id);
        if (CollectionUtils.isNotEmpty(userList)) {
            return userList.get(0);
        }
        return new LoginUser();
    }


}
