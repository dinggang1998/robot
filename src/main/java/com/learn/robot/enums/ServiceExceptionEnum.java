package com.learn.robot.enums;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义业务异常类型
 */
public enum ServiceExceptionEnum implements Serializable {

    SYS_ERROR(9999, "系统故障，请稍候重试", "系统故障，请稍候重试"),

    FORBIDDEN(403, "forbidden", "forbidden"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error", "Internal Server Error"),
    RESOURCE_EXISTS(200013, "resource exits", "resource exits"),

    ILLEGAL_PARAM(0001, "参数信息缺失", "Illegal Param"),
    LACK_PARAMS(0002, "参数错误", "Lack Params"),

    LOGIN_PWD_ERROR(1001, "登录用户名或密码错误", "登录用户名或密码错误"),
    PWD_ERROR(1002, "密码错误", "密码错误"),
    PWD_LENGTH_ERROR(1003, "密码长度需为8-16位", "密码长度需为8-16位"),
    PWD_KIND_ERROR(1004, "密码中应该至少包括数字,小写字母,大写字母,特殊符号三类", "密码长度需为8-16位"),
    PWD_HORIZONTAL_ERROR(1005, "密码中不能含有键盘横向连续输入的字符", "密码长度需为8-16位"),
    PWD_SLOPE_ERROR(1006, "密码中不能含有键盘斜向连续输入的字符", "密码中不能含有键盘斜向连续输入的字符"),
    PWD_SEQUENTIAL_ERROR(1007, "密码中不能含有逻辑位连续字符,例如\0034\",\"abcd\"", "密码中不能含有逻辑位连续字符,例如\0034\",\"abcd\""),
    PWD_SEQUENTIAL_SAME_ERROR(1008, "密码中邻位相同字符最多不能超过三位", "密码中邻位相同字符最多不能超过三位"),
    PWD_USERNAME_SAME_ERROR(1009, "密码中不能含有账户信息", "密码中不能含有账户信息"),
    PWD_NULL(1010, "密码不能为空", "密码不能为空"),
    USERNAME_NULL(1011, "账号不能为空", "账号不能为空"),
    PWD_NOT_SAME(1012, "您当前输入的密码与原密码不符", "您当前输入的密码与原密码不符"),
    PWD_FH_ERROR(1013, "您当前输入的密码中含有不合规的字符或符号", "您当前输入的密码中含有不合规的字符或符号"),
    IMGCODE_ERROR(1014, "您輸入的靜態驗證碼有誤，請重新輸入。", "您輸入的靜態驗證碼有誤，請重新輸入。"),

    USER_IS_LOCK(2001, "用户锁定！", "用户锁定！"),
    USER_IS_COLD(2002, "用户已冻结！", "用户已冻结"),
    USER_NO_LOGIN(2003, "用户未登陆", "用户未登陆"),
    NO_USER(2004, "用户不存在", "用户不存在");

    private int code;

    private String message;

    private String message_en;

    ServiceExceptionEnum(int code, String message, String message_en) {
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
     */
    public static List<String> getAllType() {
        List<String> res = new ArrayList<>();
        for (ServiceExceptionEnum item : ServiceExceptionEnum.values()) {
            res.add(String.valueOf(item.code));
        }
        List<String> myList = res.stream().distinct().collect(Collectors.toList());
        return myList;
    }
}
