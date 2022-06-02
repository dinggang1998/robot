package com.learn.robot.aspect;

import com.learn.robot.exception.RobotException;
import com.learn.robot.domain.User;
import com.learn.robot.enums.ServiceExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Order(2)
@Component
public class LoginAspect {

    @Pointcut("@annotation(com.learn.robot.aspect.Login)")
    public void login() {
    }

    /**
     * 在切点之前织入
     */
    @Before("login()")
    public void doBefore() throws Throwable {
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        User user = (User) request.getSession().getAttribute("User");
        if (user == null) {
            throw RobotException.serviceException(ServiceExceptionEnum.USER_NO_LOGIN);
        }
    }


}
