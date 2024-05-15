package com.learn.robot.service.user.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<DzUserMapper, DzUser>  implements UserService {

    @Autowired
    private DzUserMapperExt dzUserMapperExt;

    @Autowired
    DzUserMapper dzUserMapper;

    /**
     * 获取用户列表
     *
     * @return 用户列表
     */
    @Override
    public List<DzUser> getUserList() {
        // 使用lambda表达式查询用户列表
        List<DzUser> userList = lambdaQuery().eq(DzUser::getUserNo, "1").like(DzUser::getUserState, "").orderByAsc(DzUser::getUserNo).list();
//        List<DzUser> userList = dzUserMapperExt.getUserList();
        if (CollectionUtils.isNotEmpty(userList)) {
            return userList;
        }
        return new ArrayList<>();
    }

    /**
     * 根据用户ID获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     * @throws ServiceException 服务异常
     */
    @Override
    @Cacheable(value = "11", key = "#id")
    public DzUser getUserById(String id) throws ServiceException {
        if (StringUtils.isBlank(id)) {
            throw RobotException.serviceException(ServiceExceptionEnum.LACK_PARAMS);
        }
        List<DzUser> userList = dzUserMapper.selectList(Wrappers.<DzUser>lambdaQuery().eq(DzUser::getId, id));
        if (CollectionUtils.isEmpty(userList)) {
            throw RobotException.serviceException(ServiceExceptionEnum.NO_USER);
        }
        return userList.get(0);
    }
}
