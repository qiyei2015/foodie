package com.qiyei.foodie.api.controller;

import com.qiyei.foodie.api.test.TestBO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Created by qiyei2015 on 2020/8/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Api(value = "测试接口", tags = {"用于测试的相关接口"})
@RestController
public class HelloController {

    @ApiOperation(value = "测试hello", notes = "测试数据", httpMethod = "GET")
    @RequestMapping(name = "/hello" ,method = RequestMethod.GET)
    public TestBO hello(){
        return new TestBO("王大爷","123456","123456");
    }
}
