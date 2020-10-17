package com.qiyei.foodie.api.controller;

import com.qiyei.common.Response;
import com.qiyei.foodie.pojo.bo.ShopCartBO;
import com.qiyei.foodie.pojo.vo.*;
import com.qiyei.foodie.service.IItemsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Created by qiyei2015 on 2020/9/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Api(value = "购物车接口", tags = {"购物车接口的相关接口"})
@RestController
@RequestMapping("shopcart")
public class ShopCartController {
    public static final Integer PAGE_SIZE = 20;

    final static Logger logger = LoggerFactory.getLogger(ShopCartController.class);

    @Autowired
    private IItemsService mItemsService;

    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车", httpMethod = "POST")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Response<ItemsVO> list(@RequestBody ShopCartBO shopCartBO) {
        if (shopCartBO.getUserId() == null) {
            return Response.error("userId为NULL");
        }

        if (StringUtils.isBlank(shopCartBO.getItemId())) {
            return Response.error("ItemId为NULL");
        }

        System.out.println(shopCartBO);

        // TODO 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存

        return Response.success();
    }

    @ApiOperation(value = "从购物车中删除商品", notes = "从购物车中删除商品", httpMethod = "POST")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public Response<ItemsVO> delete(@RequestBody ShopCartBO shopCartBO) {
        if (StringUtils.isBlank(shopCartBO.getUserId())) {
            return Response.error("userId为NULL");
        }

        if (StringUtils.isBlank(shopCartBO.getItemId())) {
            return Response.error("ItemId为NULL");
        }

        if (StringUtils.isBlank(shopCartBO.getSpecId())) {
            return Response.error("SpecId为NULL");
        }

        // TODO 用户在页面删除购物车中的商品数据，如果此时用户已经登录，则需要同步删除后端购物车中的商品
        return Response.success();
    }


}
