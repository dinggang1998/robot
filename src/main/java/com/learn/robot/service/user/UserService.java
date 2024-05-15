package com.learn.robot.service.user;

import com.baomidou.mybatisplus.extension.service.IService;
import com.learn.robot.exception.ServiceException;
import com.learn.robot.model.user.DzUser;

import java.util.List;

public interface UserService extends IService<DzUser> {

    List<DzUser> getUserList();

    DzUser getUserById(String id) throws ServiceException;

}
