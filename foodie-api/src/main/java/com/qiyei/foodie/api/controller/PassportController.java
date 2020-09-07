package com.qiyei.foodie.api.controller;

import com.qiyei.common.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by qiyei2015 on 2020/9/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("passport")
public class PassportController {

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
    @RequestMapping(value = "usernameExist", method = RequestMethod.GET)
    public Response<String> usernameExist(@RequestParam String name) {
        return Response.createBySuccess("用户名不存在");
    }

}
