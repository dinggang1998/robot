package com.learn.robot.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义业务异常类型
 * */
public enum ServiceExceptionEnum implements Serializable {

    ILLEGAL_PARAM(200000, "参数信息缺失","Illegal Param"),
    LACK_PARAMS(200001, "参数错误","Lack Params"),
    ;

    private int code;

    private String message;

    private String message_en;

    ServiceExceptionEnum(int code, String message,String message_en) {
        this.code = code;
        this.message = message;
        this.message_en = message_en;

    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getMessageEn() {
        return message_en;
    }

    /**
     * 获取所有异常类型，配置apollo
     * */
    public static List<String> getAllType() {
        List<String> res= new ArrayList<>();
        for (ServiceExceptionEnum item : ServiceExceptionEnum.values()) {
            res.add(String.valueOf(item.code));
        }
        List<String> myList = res.stream().distinct().collect(Collectors.toList());
        return myList;
    }
}
