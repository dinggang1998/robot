package com.learn.robot.controller;

import com.alibaba.fastjson.JSONObject;
import com.learn.robot.exception.ServiceException;
import com.learn.robot.aspect.ApiLog;
import com.learn.robot.aspect.Login;
import com.learn.robot.aspect.RsaSecurityParameter;
import com.learn.robot.domain.User;
import com.learn.robot.model.Response;
import com.learn.robot.service.login.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequestMapping(value = "/api/v1/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @ApiLog(description = "登录网厅")
    @RsaSecurityParameter
    @RequestMapping(value = "/loginBusinessHall", method = RequestMethod.POST)
    public Response<User> loginBusinessHall(HttpServletRequest request, @RequestBody String jsonStr) throws ServiceException, Exception {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        String username = jsonObject.get("username") == null ? "" : jsonObject.get("username").toString();
        String pwd = jsonObject.get("pwd") == null ? "" : jsonObject.get("pwd").toString();
        log.info("loginBusinessHall | Entry the method. username = {}, pwd = {}", username, pwd);

        User user = loginService.loginBusinessHall(username, pwd);
        loginService.reGenerateSessionId(request);

        request.getSession().setAttribute("User", user);
        request.getSession().removeAttribute("userId");
        return Response.success(user);
    }

    @ApiLog(description = "退出登录")
    @RequestMapping(value = "/logOut", method = RequestMethod.POST)
    public Response logOut(HttpServletRequest request) throws Exception {
        request.getSession().removeAttribute("User");
        return Response.success();
    }


    @Login
    @ApiLog(description = "")
    @RequestMapping(value = "/test", method = RequestMethod.POST)
    public Response<String> test(HttpServletRequest request) throws Exception {
        return Response.success("1");
    }
}
