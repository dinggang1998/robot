package com.learn.robot.api;

import com.alibaba.fastjson.JSONObject;
import com.learn.robot.exception.ServiceException;
import com.learn.robot.aspect.ApiLog;
import com.learn.robot.aspect.RsaSecurityParameter;
import com.learn.robot.model.Response;
import com.learn.robot.model.user.DzUser;
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

    @ApiLog(description = "登录")
//    @RsaSecurityParameter
    @RequestMapping(value = "/loginWeb", method = RequestMethod.POST)
    public Response<DzUser> loginWeb(HttpServletRequest request, @RequestBody String jsonStr,String username,String pwd) throws ServiceException, Exception {
//        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
//        String username = jsonObject.get("username") == null ? "" : jsonObject.get("username").toString();
//        String pwd = jsonObject.get("pwd") == null ? "" : jsonObject.get("pwd").toString();
        log.info("loginBusinessHall | Entry the method. username = {}, pwd = {}", username, pwd);

        DzUser user = loginService.loginWeb(username, pwd);
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

}
