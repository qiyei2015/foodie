package com.qiyei.foodie.service;

import com.qiyei.foodie.pojo.OrderStatus;
import com.qiyei.foodie.pojo.Orders;
import com.qiyei.foodie.pojo.bo.SubmitOrderBO;
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

}
