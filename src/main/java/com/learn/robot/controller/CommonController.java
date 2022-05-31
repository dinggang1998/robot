package com.learn.robot.controller;


import com.alibaba.fastjson.JSONObject;
import com.learn.robot.exception.ServiceException;
import com.learn.robot.aspect.ApiLog;
import com.learn.robot.aspect.RsaSecurityParameter;
import com.learn.robot.model.Response;
import com.learn.robot.model.login.UuidToken;
import com.learn.robot.service.common.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


/**
 * 公共接口
 * @author StevenDing
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/common")
public class CommonController {

    @Autowired
    private CommonService commonService;



    /**
     * 获取静态验证码
     * <img width="40" height="30" src="data:image/jpg;base64,***"/>图片展示
     */
    @ApiLog(description = "获取静态验证码")
    @SuppressWarnings({"rawtypes"})
    @RequestMapping(value = "/captcha/kaptcha", method = RequestMethod.POST)
    public Response<Map<String, Object>> defaultKaptcha(HttpServletRequest request) throws ServiceException, Exception {
        Map<String, Object> resultMap= new HashMap<>();
        resultMap=commonService.defaultKaptcha();
        return Response.success(resultMap);
    }

    /**
     * 静态验证码校验
     */
    @ApiLog(description = "静态验证码校验")
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/codeVerify", method = RequestMethod.POST)
    @RsaSecurityParameter
    public Response<UuidToken> codeVerify(HttpServletRequest request, @RequestBody String jsonStr) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        String imageCode = jsonObject.getString("imagecode");
        String sessionId = jsonObject.getString("sessionId");
        commonService.verifyImageCode(imageCode, sessionId);
        // 增加随机数对称加密防止抓包后的修改
        UuidToken uuidToken = new UuidToken();
        log.info("codeVerify========> uuid:{}", uuidToken.getUuid());
        log.info("codeVerify========> uuidPlain:{}", uuidToken.getUuidPlain());
        return Response.success(uuidToken);
    }


}
