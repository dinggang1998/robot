package com.learn.robot.service.login;


import com.learn.robot.exception.ServiceException;
import com.learn.robot.model.user.DzUser;

import javax.servlet.http.HttpServletRequest;


public interface LoginService {

    /**
     * 登录获取使用人的基本信息
     */
    DzUser loginWeb(String username, String pwd) throws ServiceException, Exception;


    /**
     * 获取用户登录基本信息，如果没有登录，抛出异常
     */
    DzUser getLoginUser() throws ServiceException, Exception;

    /**
     * 强制刷新sessionid
     */
    void reGenerateSessionId(HttpServletRequest request) throws Exception;

}
