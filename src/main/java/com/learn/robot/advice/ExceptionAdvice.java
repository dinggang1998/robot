package com.learn.robot.advice;

import com.learn.robot.Exception.ServiceException;
import com.learn.robot.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:抛出业务异常
 */
@RestController
@ControllerAdvice
public class ExceptionAdvice {

    public static Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public Response handleException(Exception e) {
        Response response = new Response();
        if (e instanceof ServiceException) {
            response.setCode(((ServiceException) e).getCode());
        }
        response.setMessage("业务异常信息："+e.getMessage());
        return response;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Response handleException(RuntimeException e) {
        Response response = new Response();
        response.setCode("500");
        response.setMessage("系统异常："+e.getMessage());
        return response;
    }


    @InitBinder
    public void processParam(WebDataBinder dataBinder){
        /*
         * 创建一个字符串微调编辑器
         * 参数{boolean emptyAsNull}: 是否把空字符串("")视为 null
         */
        StringTrimmerEditor trimmerEditor = new StringTrimmerEditor(true);
        /*
         * 注册自定义编辑器
         * 接受两个参数{Class<?> requiredType, PropertyEditor propertyEditor}
         * requiredType：所需处理的类型
         * propertyEditor：属性编辑器，StringTrimmerEditor就是 propertyEditor的一个子类
         */
        dataBinder.registerCustomEditor(String.class, trimmerEditor);
        dataBinder.registerCustomEditor(Date.class,
                new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), false));
    }

}