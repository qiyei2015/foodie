package com.qiyei.foodie.api.controller;

import com.qiyei.common.Response;
import com.qiyei.foodie.pojo.Users;
import com.qiyei.foodie.pojo.bo.UserBO;
import com.qiyei.foodie.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    @Autowired
    private IUserService mUserService;

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
    @RequestMapping(value = "userExist", method = RequestMethod.GET)
    public Response<String> userExist(@RequestParam String name) {
        if (StringUtils.isBlank(name)) {
            return Response.createByErrorMessage("用户名不能为空");
        }
        Users user = mUserService.queryUserByName(name);
        if (user == null) {
            return Response.createBySuccess("用户名不存在");
        } else {
            return Response.createBySuccess("用户名已存在");
        }
    }

    @ApiOperation(value = "用户注册", notes = "用户是否存在", httpMethod = "POST")
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Response<String> register(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) {

        String name = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();

        if (StringUtils.isBlank(name) || StringUtils.isBlank(password) || StringUtils.isBlank(confirmPassword)) {
            return Response.createByErrorMessage("用户名或密码不能为空");
        }

        if (!password.equals(confirmPassword)) {
            return Response.createByErrorMessage("密码与确认密码不一致");
        }

        Users user = mUserService.queryUserByName(name);
        if (user != null) {
            return Response.createByErrorMessage("用户名已经注册，请更换用户名注册");
        }
        int index = mUserService.insertUser(userBO);
        if (index > 0) {
            return Response.createBySuccessMessage("注册成功");
        } else {
            return Response.createByErrorMessage("注册失败");
        }
    }

    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    @PostMapping(value = "login")
    public Response<String> login(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) {
        return Response.createBySuccess("用户名不存在");
    }

    @ApiOperation(value = "用户修改密码", notes = "用户修改密码", httpMethod = "POST")
    @PostMapping(value = "modifyPassword")
    public Response<String> modifyPassword(@RequestParam String oldPassword, @RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) {
        return Response.createBySuccess("用户名不存在");
    }

    @ApiOperation(value = "用户登出", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping(value = "logout")
    public Response<String> logout(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) {
        return Response.createBySuccess("用户名不存在");
    }

}
