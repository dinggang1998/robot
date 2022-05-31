package com.learn.robot.service.common;


import com.learn.robot.exception.ServiceException;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface CommonService {

    /**
     * 获取session
     */
    HttpSession getSession() throws Exception;

    /**
     * 获取图片验证码
     */
    Map<String, Object>  defaultKaptcha() throws ServiceException,Exception;

    /**
     * 校验图片验证码
     */
    void verifyImageCode(String imageCode, String sessionId) throws Exception;


}
