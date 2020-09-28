package com.qiyei.foodie.service;

import com.qiyei.foodie.pojo.Items;
import com.qiyei.foodie.pojo.ItemsImg;
import com.qiyei.foodie.pojo.ItemsParam;
import com.qiyei.foodie.pojo.ItemsSpec;
import com.qiyei.foodie.pojo.bo.ItemsBO;
import com.qiyei.foodie.pojo.vo.CommentLevelCountsVO;
import com.qiyei.foodie.pojo.vo.ItemsCommentsVO;
import com.qiyei.foodie.pojo.vo.ItemsVO;
import com.qiyei.foodie.pojo.vo.ShopCartVO;

import java.util.List;

/**
 * @author Created by qiyei2015 on 2020/9/8.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public interface IItemsService {

    ItemsVO queryItems(ItemsBO itemsBO);

    ItemsVO searchItems(ItemsBO itemsBO);

    Items queryItemsById(String id);

    /**
     * 根据商品id查询商品图片列表
     * @param itemId
     * @return
     */
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查询商品规格
     * @param itemId
     * @return
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id查询商品参数
     * @param itemId
     * @return
     */
    ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品id查询商品的评价等级数量
     * @param id
     * @return
     */
    CommentLevelCountsVO queryCommentCounts(String id);

    /**
     * 根据商品id查询商品的评价（分页）
     * @param id
     * @return
     */
    ItemsCommentsVO queryComment(String id);

    /**
     * 根据规格ids查询最新的购物车中商品数据（用于刷新渲染购物车中的商品数据）
     * @param ids
     * @return
     */
    List<ShopCartVO> queryItemsBySpecIds(List<String> ids);


}
