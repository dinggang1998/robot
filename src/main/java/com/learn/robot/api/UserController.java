package com.learn.robot.api;


import com.alibaba.fastjson.JSON;
import com.learn.robot.exception.ServiceException;
import com.learn.robot.aspect.ApiLog;
import com.learn.robot.aspect.RsaSecurityParameter;
import com.learn.robot.domain.LoginUser;
import com.learn.robot.model.Response;
import com.learn.robot.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("用户信息")
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class UserController {

    @Autowired
    UserService userService;

    @ApiOperation("获取所有用户信息")
    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    public List<LoginUser> getUserList() {
        return userService.getUserList();
    }

    @ApiOperation("根据id获取用户信息")
    @ApiLog(description = "根据id获取用户信息")
    @PostMapping("/getUserById")
    @RsaSecurityParameter(outEncode = true)
    public Response<LoginUser> getUserById(@RequestBody String id) throws ServiceException {
        return Response.success(userService.getUserById(id));
    }

    @ApiOperation("根据id获取用户信息")
    @ApiLog(description = "根据id获取用户信息")
    @PostMapping("/getUserByIdRSA")
    @RsaSecurityParameter(inDecode = true, outEncode = true)
    public Response<LoginUser> getUserByIdRSA(@RequestBody String jsonStr) throws ServiceException, Exception {
        LoginUser loginUser = JSON.parseObject(jsonStr, LoginUser.class);
        return Response.success(userService.getUserById(loginUser.getId()));
    }

}
