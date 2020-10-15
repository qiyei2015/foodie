package com.qiyei.foodie.service;

import com.qiyei.common.PagedGrid;
import com.qiyei.foodie.pojo.OrderItems;
import com.qiyei.foodie.pojo.Users;
import com.qiyei.foodie.pojo.bo.OrderItemsCommentBO;
import com.qiyei.foodie.pojo.bo.UserBO;
import com.qiyei.foodie.pojo.bo.UserCenterBO;

import java.util.List;

/**
 * @author Created by qiyei2015 on 2020/9/8.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public interface ICommentService {

    /**
     * 查询待评论的商品
     * @param orderId
     * @return
     */
    List<OrderItems> queryPendingComment(String orderId);

    /**
     * 保存评论
     * @param orderId
     * @param userId
     * @param commentList
     * @return
     */
    int saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList);

    /**
     * 查询我的评论
     * @return
     */
    PagedGrid queryMyComments(String userId, int page, int pageSize);
}
