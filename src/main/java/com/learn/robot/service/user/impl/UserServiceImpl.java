package com.learn.robot.service.user.impl;

import com.learn.robot.exception.RobotException;
import com.learn.robot.exception.ServiceException;
import com.learn.robot.dao.DzUserMapperExt;
import com.learn.robot.enums.ServiceExceptionEnum;
import com.learn.robot.mapper.DzUserMapper;
import com.learn.robot.model.user.DzUser;
import com.learn.robot.service.user.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private DzUserMapperExt dzUserMapperExt;

    @Autowired
    DzUserMapper dzUserMapper;

    @Override
    public List<DzUser> getUserList() {
        List<DzUser> userList = dzUserMapperExt.getUserList();
        if (CollectionUtils.isNotEmpty(userList)) {
            return userList;
        }
        return new ArrayList<>();
    }

    @Override
    @Cacheable(value = "11", key = "#id")
    public DzUser getUserById(String id) throws ServiceException {
        if (StringUtils.isBlank(id)) {
            throw RobotException.serviceException(ServiceExceptionEnum.LACK_PARAMS);
        }
        List<DzUser> userList = dzUserMapper.selectByIds(id);
        if (CollectionUtils.isNotEmpty(userList)) {
            return userList.get(0);
        }
        return new DzUser();
    }


}
