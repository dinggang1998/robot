package com.learn.robot.advice;

import com.alibaba.fastjson.JSONObject;
import com.learn.robot.aspect.RsaSecurityParameter;
import com.learn.robot.model.Response;
import com.learn.robot.util.RSAUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;


import java.security.PublicKey;

/**
 * @MethodName: 请求请求处理类（目前仅仅对responsebody有效）
 * * 对加了@Eecrypt的方法的数据进行加密操作
 * @Param
 * @return
 **/
@Slf4j
@ControllerAdvice(basePackages = "com.learn.robot")
public class EncryptRequestBodyAdvice implements ResponseBodyAdvice {

    @Value("${spring.encrypt.publicKey}")
    String publicKey;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        try {
            boolean encode = false;
            if (returnType.getMethod().isAnnotationPresent(RsaSecurityParameter.class)) {
                RsaSecurityParameter serializedField = returnType.getMethodAnnotation(RsaSecurityParameter.class);
                encode = serializedField.outEncode();
            }
            if (encode) {
                return EncryptHttpOutputMessage(body, publicKey, "utf-8");
            } else {
                return body;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("对方法method :【" + returnType.getMethod().getName() + "】返回数据进行加密出现异常：" + e.getMessage());
            return body;
        }
    }


    public Object EncryptHttpOutputMessage(Object body, String publicKey, String charset)throws Exception {

        if (StringUtils.isEmpty(publicKey)) {
            throw new IllegalArgumentException("publicKey is null");
        }
        Response response = (Response) body;
        //未加密数据不进行加密操作
        if ("200".equals(response.getCode())) {
            log.info("=======>加密前:{}", response.getData());
            PublicKey publicKey1 = RSAUtils.string2PublicKey(publicKey);
            //用公钥加密
            byte[] publicEncrypt = RSAUtils.publicEncrypt(JSONObject.toJSONString(response.getData()).getBytes(), publicKey1);
            String byte2Base64 = RSAUtils.byte2Base64(publicEncrypt);
            log.info("=======>加密后:{}", byte2Base64);
            response.setData(byte2Base64);
            return (Object)response;
        }
        return body;
    }

}