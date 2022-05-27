package com.learn.robot.advice;

import com.learn.robot.aspect.RsaSecurityParameter;
import com.learn.robot.util.Base64Utils;
import com.learn.robot.util.RSAUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.security.PrivateKey;

/**
 * @MethodName: 请求请求处理类（目前仅仅对requestbody有效）
 *  * 对加了@Decrypt的方法的数据进行解密密操作
 * @Param
 * @return
 **/
@Slf4j
@ControllerAdvice(basePackages = "com.learn.robot")
public class DecryptRequestBodyAdvice implements RequestBodyAdvice {


    @Value("${spring.encrypt.privateKey}")
    String privateKey;

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                  Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter,
                                Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType,
                                           Class<? extends HttpMessageConverter<?>> converterType) throws IOException {

        try {
            boolean encode = false;
            if (parameter.getMethod().isAnnotationPresent(RsaSecurityParameter.class)) {
//                log.info("对方法method :【" + parameter.getMethod().getName() + "】返回数据进行解密");
                //获取注解配置的包含和去除字段
                RsaSecurityParameter serializedField = parameter.getMethodAnnotation(RsaSecurityParameter.class);
                //入参是否需要解密
                encode = serializedField.inDecode();
            }
            if (encode) {
//                log.info("对方法method :【" + parameter.getMethod().getName() + "】返回数据进行解密");
                return new DecryptHttpInputMessage(inputMessage, privateKey, "utf-8");
            }else{
                return inputMessage;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("对方法method :【" + parameter.getMethod().getName() + "】返回数据进行解密出现异常："+e.getMessage());
            return inputMessage;
        }

    }

}

@Slf4j
class DecryptHttpInputMessage implements HttpInputMessage {

    private HttpHeaders headers;
    private InputStream body;

    public DecryptHttpInputMessage(HttpInputMessage inputMessage, String privateKey, String charset) throws Exception {

        if(StringUtils.isEmpty(privateKey)){
            throw new IllegalArgumentException("privateKey is null");
        }
//        log.info("=======>privateKey:{}",privateKey);
        //获取请求内容
        this.headers = inputMessage.getHeaders();
        String content = IOUtils.toString(inputMessage.getBody(), charset);

        //未加密数据不进行解密操作
        String decryptBody;
        if (content.startsWith("{")) {
            decryptBody = content;
        } else {
//            log.info("=======>解密");
            StringBuilder json = new StringBuilder();
            content = content.replaceAll(" ", "+");

            if (!StringUtils.isEmpty(content)) {
                String[] contents = content.split("\\|");
                for (int k = 0; k < contents.length; k++) {
                    String value = contents[k];
                    log.info("=======>解密前:{}",value);
                    PrivateKey privateKey1 = RSAUtils.string2PrivateKey(privateKey);
                    value = new String(RSAUtils.privateDecrypt(Base64Utils.decode(value), privateKey1), charset);
                    String de_value = URLDecoder.decode(value,"UTF-8");
                    log.info("=======>解密后:{}",de_value);
                    json.append(de_value);
                }
            }
            decryptBody = json.toString();
        }
        this.body = IOUtils.toInputStream(decryptBody, charset);
    }

    @Override
    public InputStream getBody() throws IOException {
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }
}
