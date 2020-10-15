package com.qiyei.foodie.service;

import com.qiyei.common.PagedGrid;
import com.qiyei.foodie.pojo.OrderStatus;
import com.qiyei.foodie.pojo.Orders;
import com.qiyei.foodie.pojo.bo.SubmitOrderBO;
import com.qiyei.foodie.pojo.vo.OrderStatusCountsVO;
import com.qiyei.foodie.pojo.vo.OrderVO;

/**
 * @author Created by qiyei2015 on 2020/9/8.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public interface IOrdersService {
    /**
     * 查询订单
     * @param userId
     * @param orderId
     * @return
     */
    Orders queryOrder(String userId,String orderId);

    /**
     * 创建订单
     * @param submitOrderBO
     * @return
     */
    OrderVO createOrder(SubmitOrderBO submitOrderBO);

    /**
     * 查询订单状态
     * @param orderId
     * @return
     */
    OrderStatus queryOrderStatus(String orderId);

    /**
     * 更新订单状态
     * @param merchantOrderId
     * @return
     */
    Integer updateOrderStatus(String merchantOrderId, Integer orderStatus);

    /**
     * 获得订单状态数概况
     * @param userId
     * @return
     */
    OrderStatusCountsVO getOrderStatusCounts(String userId);

    /**
     * 更新订单状态 —> 确认收货
     *
     * @return
     */
    boolean updateReceiveOrderStatus(String orderId);

    /**
     * 删除订单（逻辑删除）
     * @param userId
     * @param orderId
     * @return
     */
    boolean deleteOrder(String userId, String orderId);

    /**
     * 获得分页的订单动向
     * @param userId
     * @param page
     * @param pageSize
     * @return
     */
    PagedGrid getOrdersTrend(String userId, Integer page, Integer pageSize);

    /**
     * 查询我的订单列表
     *
     * @param userId
     * @param orderStatus
     * @param page
     * @param pageSize
     * @return
     */
    PagedGrid queryMyOrders(String userId, Integer orderStatus, Integer page, Integer pageSize);

    /**
     * @Description: 订单状态 --> 商家发货
     */
    void updateDeliverOrderStatus(String orderId);
}
