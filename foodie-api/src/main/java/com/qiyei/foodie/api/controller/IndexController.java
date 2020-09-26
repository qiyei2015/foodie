package com.qiyei.foodie.api.controller;

import com.qiyei.common.Response;
import com.qiyei.common.enums.BooleanEnum;
import com.qiyei.foodie.pojo.Carousel;
import com.qiyei.foodie.pojo.Category;
import com.qiyei.foodie.pojo.Items;
import com.qiyei.foodie.service.ICarouselService;
import com.qiyei.foodie.service.ICategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Created by qiyei2015 on 2020/9/7.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Api(value = "首页", tags = {"展示首页相关的api"})
@RestController
@RequestMapping("index")
public class IndexController {

    final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private ICarouselService mCarouselService;

    @Autowired
    private ICategoryService mCategoryService;

    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    @RequestMapping(value = "carousel", method = RequestMethod.GET)
    public Response<List<Carousel>> carousel() {
        return Response.success(mCarouselService.queryAll(BooleanEnum.YES.type));
    }

    @ApiOperation(value = "获取商品分类(一级分类", notes = "获取商品分类(一级分类)", httpMethod = "GET")
    @RequestMapping(value = "category", method = RequestMethod.GET)
    public Response<List<Category>> category() {
        return Response.success(mCategoryService.queryAllRootCategory());
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping(value = "subCategory/{parentId}")
    public Response<List<Category>> subCategory(@ApiParam(name = "parentId", value = "一级分类id", required = true) @PathVariable Integer parentId) {
        return Response.success(mCategoryService.querySubCategoryByParent(parentId));
    }

    @ApiOperation(value = "查询每个一级分类下的最新6条商品数据", notes = "查询每个一级分类下的最新6条商品数据", httpMethod = "GET")
    @GetMapping(value = "items/{id}")
    public Response<List<Items>> items(@ApiParam(name = "id", value = "一级分类id", required = true) @PathVariable Integer id) {
        return Response.success(mCategoryService.queryItemsByCategory(id,10));
    }

}
