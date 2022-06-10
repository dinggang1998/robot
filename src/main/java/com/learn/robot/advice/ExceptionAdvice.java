//package com.learn.robot.advice;
//
//
//import com.learn.robot.exception.ServiceException;
//import com.learn.robot.model.Response;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.propertyeditors.CustomDateEditor;
//import org.springframework.beans.propertyeditors.StringTrimmerEditor;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
///**
// * @Description:抛出业务异常
// */
//@Slf4j
//@RestController
//@ControllerAdvice
//public class ExceptionAdvice {
//
//
//    @ResponseBody
//    @ExceptionHandler(ServiceException.class)
//    public Response handleException(Exception e) {
//        Response response = new Response();
//        if (e instanceof ServiceException) {
//            response.setCode(((ServiceException) e).getCode());
//        }
//        response.setMessage("业务异常：[" + e.getMessage() + "] ");
//        log.error("业务异常：" + e.getMessage(), e);
//        return response;
//    }
//
//    @ExceptionHandler(RuntimeException.class)
//    @ResponseBody
//    public Response handleException(RuntimeException e, HttpServletRequest request) {
//        Response response = new Response();
//        response.setCode("500");
//        response.setMessage("接口 [" + request.getRequestURI() + "] 内部错误，请联系管理员");
//        log.error("系统内部错误：" + e.getMessage(), e);
//        return response;
//    }
//
//
//    @InitBinder
//    public void processParam(WebDataBinder dataBinder) {
//        /*
//         * 创建一个字符串微调编辑器
//         * 参数{boolean emptyAsNull}: 是否把空字符串("")视为 null
//         */
//        StringTrimmerEditor trimmerEditor = new StringTrimmerEditor(true);
//        /*
//         * 注册自定义编辑器
//         * 接受两个参数{Class<?> requiredType, PropertyEditor propertyEditor}
//         * requiredType：所需处理的类型
//         * propertyEditor：属性编辑器，StringTrimmerEditor就是 propertyEditor的一个子类
//         */
//        dataBinder.registerCustomEditor(String.class, trimmerEditor);
//        dataBinder.registerCustomEditor(Date.class,
//                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));
//    }
//
//
//}