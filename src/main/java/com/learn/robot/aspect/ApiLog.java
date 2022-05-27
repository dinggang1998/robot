package com.learn.robot.aspect;


import java.lang.annotation.*;

/**
 * AOP日志切面注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ApiLog {

    String description() default "";

}
