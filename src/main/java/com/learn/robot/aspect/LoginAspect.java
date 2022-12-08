package com.learn.robot.aspect;

import com.learn.robot.exception.RobotException;
import com.learn.robot.enums.ServiceExceptionEnum;
import com.learn.robot.model.user.DzUser;
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
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        DzUser user = (DzUser) request.getSession().getAttribute("User");
        if (user == null) {
            throw RobotException.serviceException(ServiceExceptionEnum.USER_NO_LOGIN);
        }
    }


}
