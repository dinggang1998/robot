package com.learn.robot.aspect;


import java.lang.annotation.*;

/**
 * 登录确认
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface Login {

}
