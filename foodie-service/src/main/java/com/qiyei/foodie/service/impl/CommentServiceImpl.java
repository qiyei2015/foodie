package com.qiyei.foodie.service.impl;

import com.github.pagehelper.PageHelper;
import com.qiyei.common.PagedGrid;
import com.qiyei.common.enums.BooleanEnum;
import com.qiyei.foodie.mapper.ItemsCommentsMapperCustom;
import com.qiyei.foodie.mapper.OrderItemsMapper;
import com.qiyei.foodie.mapper.OrderStatusMapper;
import com.qiyei.foodie.mapper.OrdersMapper;
import com.qiyei.foodie.pojo.OrderItems;
import com.qiyei.foodie.pojo.OrderStatus;
import com.qiyei.foodie.pojo.Orders;
import com.qiyei.foodie.pojo.bo.OrderItemsCommentBO;
import com.qiyei.foodie.pojo.vo.CommentVO;
import com.qiyei.foodie.service.BaseService;
import com.qiyei.foodie.service.ICommentService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CommentServiceImpl extends BaseService implements ICommentService {

    @Autowired
    public OrderItemsMapper mOrderItemsMapper;

    @Autowired
    public OrdersMapper mOrdersMapper;

    @Autowired
    public OrderStatusMapper mOrderStatusMapper;

    @Autowired
    public ItemsCommentsMapperCustom mItemsCommentsMapperCustom;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<OrderItems> queryPendingComment(String orderId) {
        OrderItems query = new OrderItems();
        query.setOrderId(orderId);
        return mOrderItemsMapper.select(query);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList) {
        // 1. 保存评价 items_comments
        for (OrderItemsCommentBO oic : commentList) {
            oic.setCommentId(sid.nextShort());
        }
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("commentList", commentList);
        mItemsCommentsMapperCustom.saveComments(map);

        // 2. 修改订单表改已评价 orders
        Orders order = new Orders();
        order.setId(orderId);
        order.setIsComment(BooleanEnum.YES.type);
        mOrdersMapper.updateByPrimaryKeySelective(order);

        // 3. 修改订单状态表的留言时间 order_status
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setCommentTime(new Date());
        mOrderStatusMapper.updateByPrimaryKeySelective(orderStatus);
        return true;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGrid queryMyComments(String userId, int page, int pageSize) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);

        PageHelper.startPage(page, pageSize);
        List<CommentVO> list = mItemsCommentsMapperCustom.queryMyComments(map);

        return setterPagedGrid(list, page);
    }
}
