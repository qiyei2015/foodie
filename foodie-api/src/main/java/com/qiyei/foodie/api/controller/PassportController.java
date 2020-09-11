package com.qiyei.foodie.api.controller;

import com.qiyei.common.Response;
import com.qiyei.common.utils.CookieUtils;
import com.qiyei.common.utils.JsonUtils;
import com.qiyei.common.utils.MD5Utils;
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

    private static final String KEY_USER = "key_user";

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
    public Response<Users> login(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) {
        String name = userBO.getUsername();
        String password = userBO.getPassword();
        if (StringUtils.isBlank(name) || StringUtils.isBlank(password)) {
            return Response.createByErrorMessage("用户名或密码不能为空");
        }
        Users user = null;
        try {
            //实现登录
            user = mUserService.queryUser(name, MD5Utils.getMD5Str(password));
            if (user == null) {
                return Response.createByErrorMessage("用户名或密码不正确");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.createByErrorMessage("密码异常");
        }

        user = setNullProperty(user);
        CookieUtils.setCookie(request, response, KEY_USER, JsonUtils.toJson(user), true);

        // TODO: 2020/9/11  生成用户token，存入redis会话
        // TODO 同步购物车数据

        return Response.createBySuccess(user);
    }

    @ApiOperation(value = "用户修改密码", notes = "用户修改密码", httpMethod = "POST")
    @PostMapping(value = "modifyPassword")
    public Response<String> modifyPassword(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response) {

        String name = userBO.getUsername();
        String oldPassword = userBO.getOldPassword();

        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();

        if (StringUtils.isBlank(name) || StringUtils.isBlank(password) || StringUtils.isBlank(oldPassword) || StringUtils.isBlank(confirmPassword)) {
            return Response.createByErrorMessage("用户名或密码不能为空");
        }
        if (!password.equals(confirmPassword)) {
            return Response.createByErrorMessage("密码与确认密码不一致");
        }

        Users user = null;
        try {
            //实现登录
            user = mUserService.queryUser(name, MD5Utils.getMD5Str(oldPassword));
            if (user == null) {
                return Response.createByErrorMessage("用户名或旧密码不正确");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.createByErrorMessage("旧密码异常");
        }

        int count = mUserService.updateUser(userBO);
        if (count > 0) {
            return Response.createBySuccess("修改密码成功");
        } else {
            return Response.createByErrorMessage("修改密码失败");
        }
    }

    @ApiOperation(value = "用户登出", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping(value = "logout")
    public Response<String> logout(@RequestBody String userId, HttpServletRequest request, HttpServletResponse response) {
        // 清除用户的相关信息的cookie
        CookieUtils.deleteCookie(request, response, KEY_USER);
        return Response.createBySuccess("退出登录成功");
    }

    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }
}
