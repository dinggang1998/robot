package com.learn.robot.service;

import com.learn.robot.Exception.ServiceException;
import com.learn.robot.domain.LoginUser;

import java.util.List;

public interface LoginService {

    List<LoginUser> getUserList();

    LoginUser getUserById(String id) throws ServiceException;

}
