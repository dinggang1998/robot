package com.learn.robot.controller;


import com.learn.robot.Exception.ServiceException;
import com.learn.robot.aspect.ApiLog;
import com.learn.robot.aspect.RsaSecurityParameter;
import com.learn.robot.domain.LoginUser;
import com.learn.robot.model.Response;
import com.learn.robot.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api("用户信息")
@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    LoginService loginService;

    @ApiOperation("获取所有用户信息")
    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    public List<LoginUser> getUserList() {
        return loginService.getUserList();
    }

    @ApiOperation("根据id获取用户信息")
    @ApiLog(description = "根据id获取用户信息")
    @PostMapping("/getUserById")
    @RsaSecurityParameter(inDecode = false,outEncode = false)
    public Response<LoginUser> getUserById(@RequestBody String id) throws ServiceException {
        return Response.success(loginService.getUserById(id));
    }

}
