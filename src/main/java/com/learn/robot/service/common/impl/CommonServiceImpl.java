package com.learn.robot.service.common.impl;


import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.learn.robot.exception.RobotException;
import com.learn.robot.exception.ServiceException;
import com.learn.robot.enums.ServiceExceptionEnum;
import com.learn.robot.service.common.CommonService;
import com.learn.robot.util.Base64Utils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class CommonServiceImpl implements CommonService {

    @Lazy
    @Autowired
    private CommonService commonService;

    @Autowired
    private DefaultKaptcha captchaProducer;

    @Override
    public HttpSession getSession() throws Exception {
        return  ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();
    }

    @Override
    public Map<String, Object> defaultKaptcha() throws ServiceException,Exception{
        Map<String, Object> resultMap = new HashMap<>();
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        HttpSession session = commonService.getSession();
        String sessionID = session.getId();
        log.info("defaultKaptcha | sessionID code :" + sessionID);
        try {
            //生产验证码字符串并保存到session中
            String createText = captchaProducer.createText();
            log.info("defaultKaptcha | Generate verification code :" + createText);
            session.setAttribute(sessionID, createText);
            //使用生产的验证码字符串返回一个BufferedImage对象并转为byte写入到byte数组中
            BufferedImage challenge = captchaProducer.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            throw RobotException.serviceException(ServiceExceptionEnum.SYS_ERROR);
        }
        //定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        String resultString = Base64Utils.encode(jpegOutputStream.toByteArray());
        resultMap.put("sessionId", sessionID);
        resultMap.put("codeImg", resultString);
        return resultMap;
    }

    @Override
    public void verifyImageCode(String imageCode, String sessionId) throws Exception {
        if (!StringUtils.isNotBlank(imageCode) && !StringUtils.isNotBlank(sessionId)) {
            throw RobotException.serviceException(ServiceExceptionEnum.LACK_PARAMS);
        }
        log.info("codeVerify | sessionID code :" + sessionId);
        HttpSession session = commonService.getSession();
               ;
        //这里是得到相应的验证码a
        String kaptchaExpected = (String) session.getAttribute(sessionId);
        log.info("codeVerify | kaptchaExpected = {}", kaptchaExpected);
        log.info("imageCode:{}", imageCode);
        if (!imageCode.equalsIgnoreCase(kaptchaExpected)) {
            throw RobotException.serviceException(ServiceExceptionEnum.IMGCODE_ERROR);
        }
    }
}
