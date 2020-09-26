package com.qiyei.foodie.api.controller;

import com.qiyei.common.Response;
import com.qiyei.foodie.pojo.Items;
import com.qiyei.foodie.pojo.ItemsImg;
import com.qiyei.foodie.pojo.ItemsParam;
import com.qiyei.foodie.pojo.ItemsSpec;
import com.qiyei.foodie.pojo.bo.ItemsBO;
import com.qiyei.foodie.pojo.vo.*;
import com.qiyei.foodie.service.IItemsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
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
@Api(value = "商品接口", tags = {"商品信息展示的相关接口"})
@RestController
@RequestMapping("items")
public class ItemsController {
    public static final Integer PAGE_SIZE = 20;

    final static Logger logger = LoggerFactory.getLogger(ItemsController.class);

    @Autowired
    private IItemsService mItemsService;

    @ApiOperation(value = "通过分类id获取商品列表", notes = "通过分类id获取商品列表", httpMethod = "POST")
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Response<ItemsVO> list(@RequestBody ItemsBO itemsBO) {
        if (itemsBO.getCategoryId() == null) {
            return Response.errorMessage("categoryId为NULL");
        }
        if (itemsBO.getPage() == null) {
            itemsBO.setPage(0);
        }
        if (itemsBO.getPageSize() == null) {
            itemsBO.setPage(PAGE_SIZE);
        }

        return Response.success(mItemsService.queryItems(itemsBO));
    }

    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "POST")
    @RequestMapping(value = "search", method = RequestMethod.POST)
    public Response<ItemsVO> search(@RequestBody ItemsBO itemsBO) {
        if (StringUtils.isBlank(itemsBO.getKeywords())) {
            return Response.errorMessage("keywords为NULL");
        }
        if (itemsBO.getPage() == null) {
            itemsBO.setPage(0);
        }
        if (itemsBO.getPageSize() == null) {
            itemsBO.setPage(PAGE_SIZE);
        }
        return Response.success(mItemsService.searchItems(itemsBO));
    }

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @RequestMapping(value = "info/{itemId}", method = RequestMethod.GET)
    public Response<ItemsInfoVO> info(@ApiParam(name = "itemId", value = "商品id", required = true) @PathVariable String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return Response.errorMessage("itemId为NULL");
        }

        Items item = mItemsService.queryItemsById(itemId);
        List<ItemsImg> itemImgList = mItemsService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = mItemsService.queryItemSpecList(itemId);
        ItemsParam itemsParam = mItemsService.queryItemParam(itemId);

        ItemsInfoVO itemsInfoVO = new ItemsInfoVO();
        itemsInfoVO.setItem(item);
        itemsInfoVO.setItemImgList(itemImgList);
        itemsInfoVO.setItemSpecList(itemsSpecList);
        itemsInfoVO.setItemParams(itemsParam);

        return Response.success(itemsInfoVO);
    }

    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "POST")
    @RequestMapping(value = "commentLevel", method = RequestMethod.POST)
    public Response<CommentLevelCountsVO> commentLevel(@RequestBody ItemsBO itemsBO) {
        if (StringUtils.isBlank(itemsBO.getCategoryId())) {
            return Response.errorMessage("itemId为NULL");
        }

        CommentLevelCountsVO countsVO = mItemsService.queryCommentCounts(itemsBO.getCategoryId());
        return Response.success(countsVO);
    }

    @ApiOperation(value = "查询商品评论", notes = "查询商品评论", httpMethod = "POST")
    @RequestMapping(value = "comments", method = RequestMethod.POST)
    public Response<ItemsCommentVO> comments(@RequestBody ItemsBO itemsBO) {
        if (StringUtils.isBlank(itemsBO.getCategoryId())) {
            return Response.errorMessage("itemId为NULL");
        }
        ItemsCommentVO countsVO = mItemsService.queryComment(itemsBO.getCategoryId());
        return Response.success(countsVO);
    }

    @ApiOperation(value = "根据商品规格ids查找最新的商品数据", notes = "根据商品规格ids查找最新的商品数据", httpMethod = "POST")
    @RequestMapping(value = "refresh", method = RequestMethod.POST)
    public Response<List<ShopCartVO>> refresh(@RequestBody List<String> list) {
        if (list == null || list.size() == 0) {
            return Response.errorMessage("ids为NULL");
        }
        return Response.success(mItemsService.queryItemsBySpecIds(list));
    }
}
