//package com.learn.robot.config;
//
//import com.alibaba.fastjson.JSON;
//import com.learn.robot.Exception.ServiceException;
//import com.learn.robot.model.Response;
//import com.learn.robot.model.ResultCode;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.NoHandlerFoundException;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//
///**
// * Spring MVC 配置
// */
//@Configuration
//public class WebMvcConfigurer extends WebMvcConfigurerAdapter {
//
//    private final Logger logger = LoggerFactory.getLogger(WebMvcConfigurer.class);
//
//    /** 换行符 */
//    private static final String LINE_SEPARATOR = System.lineSeparator();
//
//
//
//    //统一异常处理
//    @Override
//    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers) {
//        exceptionResolvers.add(new HandlerExceptionResolver() {
//            public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) {
//                Response result = new Response();
//                if (e instanceof ServiceException) {
//                    //业务失败的异常，如“账号或密码错误”
//                    ServiceException serviceException = (ServiceException)e;
//                    result.setCode(serviceException.getCode());
//                    result.setMessage(serviceException.getMessage());
//                }else if (e instanceof NoHandlerFoundException) {
//                    result.setCode(ResultCode.NOT_FOUND.toString());
//                    result.setMessage("接口 [" + request.getRequestURI() + "] 不存在");
//                } else if (e instanceof ServletException) {
//                    result.setCode(ResultCode.FAIL.toString());
//                    result.setMessage(e.getMessage());
//                } else {
//                    result.setCode(ResultCode.INTERNAL_SERVER_ERROR.toString());
//                    result.setMessage("接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员");
//                    String message;
//                    if (handler instanceof HandlerMethod) {
//                        HandlerMethod handlerMethod = (HandlerMethod) handler;
//                        message = String.format("接口 [%s] 出现异常，方法：%s.%s，异常摘要：%s",
//                                request.getRequestURI(),
//                                handlerMethod.getBean().getClass().getName(),
//                                handlerMethod.getMethod().getName(),
//                                e.getMessage());
//                    } else {
//                        message = e.getMessage();
//                    }
//                    logger.error(message, e);
//                }
//                responseResult(response, result);
//                return new ModelAndView();
//            }
//
//        });
//    }
//
//    //解决跨域问题
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedMethods("*")
//                .allowedHeaders("*")
//                .allowedOrigins("*");
//    }
//
//
//    private void responseResult(HttpServletResponse response, Response result) {
//        response.setCharacterEncoding("UTF-8");
//        response.setHeader("Content-type", "application/json;charset=UTF-8");
//        response.setStatus(200);
//        try {
//            response.getWriter().write(JSON.toJSONString(result));
//        } catch (IOException ex) {
//            logger.error(ex.getMessage());
//        }
//    }
//}
