package com.learn.robot.controller;


import com.alibaba.fastjson.JSONObject;
import com.learn.robot.Exception.RobotException;
import com.learn.robot.Exception.ServiceException;
import com.learn.robot.aspect.ApiLog;
import com.learn.robot.aspect.RsaSecurityParameter;
import com.learn.robot.domain.User;
import com.learn.robot.enums.ServiceExceptionEnum;
import com.learn.robot.model.Response;
import com.learn.robot.model.login.UuidToken;
import com.learn.robot.service.common.CommonService;
import com.learn.robot.util.RSAUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.security.PublicKey;
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

    @Value("${spring.encrypt.publicKey}")
    String publicKey;

    /**
     * 获取静态验证码
     * @throws Exception <img width="40" height="30" src="data:image/jpg;base64,***"/>图片展示
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
     *
     * @return
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

    /**
     * 测试加密
     */
    @ApiLog(description = "加密")
    @RequestMapping(value = "/serect", method = RequestMethod.POST)
    public Response<String> serect(HttpServletRequest request, @RequestBody JSONObject jsonObject) throws Exception {
        log.info("=======>加密前:{}", jsonObject);
        PublicKey publicKey1 = RSAUtils.string2PublicKey(publicKey);
        byte[] publicEncrypt = RSAUtils.publicEncrypt(jsonObject.toString().getBytes(), publicKey1);
        String byte2Base64 = RSAUtils.byte2Base64(publicEncrypt);
        log.info("=======>加密后:{}", byte2Base64);
        return Response.success(byte2Base64);
    }

}
