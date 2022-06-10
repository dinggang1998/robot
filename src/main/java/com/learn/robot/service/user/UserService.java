package com.learn.robot.service.user;

import com.learn.robot.exception.ServiceException;
import com.learn.robot.model.user.DzUser;

import java.util.List;

public interface UserService {

    List<DzUser> getUserList();

    DzUser getUserById(String id) throws ServiceException;

}
