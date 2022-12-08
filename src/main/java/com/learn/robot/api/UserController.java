package com.learn.robot.api;

import com.alibaba.fastjson.JSON;
import com.learn.robot.exception.ServiceException;
import com.learn.robot.aspect.ApiLog;
import com.learn.robot.aspect.RsaSecurityParameter;
import com.learn.robot.model.Response;
import com.learn.robot.model.user.DzUser;
import com.learn.robot.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    @ApiLog(description = "获取所有用户信息")
    @RequestMapping(value = "/getUserList", method = RequestMethod.POST)
    public List<DzUser> getUserList() {
        return userService.getUserList();
    }

    @ApiOperation("根据id获取用户信息")
    @ApiLog(description = "根据id获取用户信息")
    @PostMapping("/getUserById")
    @RsaSecurityParameter(inDecode = false,outEncode = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户ID", required = false,
                    dataType = "String", paramType = "query", defaultValue = "true"),
    })
    public Response<DzUser> getUserById(@RequestBody String id) throws ServiceException {
        return Response.success(userService.getUserById(id));
    }

    @ApiOperation("根据id获取用户信息")
    @ApiLog(description = "根据id获取用户信息")
    @PostMapping("/getUserByUser")
    @RsaSecurityParameter(outEncode = true)
    public Response<DzUser> getUserByIdRSA(@RequestBody String jsonStr) throws  Exception {
        DzUser dzUser = JSON.parseObject(jsonStr, DzUser.class);
        return Response.success(userService.getUserById(dzUser.getId()));
    }

}
