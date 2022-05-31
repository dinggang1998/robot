package com.learn.robot.service.user;

import com.learn.robot.exception.ServiceException;
import com.learn.robot.domain.LoginUser;

import java.util.List;

public interface UserService {

    List<LoginUser> getUserList();

    LoginUser getUserById(String id) throws ServiceException;

}
