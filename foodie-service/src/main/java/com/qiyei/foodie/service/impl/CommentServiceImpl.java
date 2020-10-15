package com.qiyei.foodie.service.impl;

import com.qiyei.common.PagedGrid;
import com.qiyei.foodie.pojo.OrderItems;
import com.qiyei.foodie.pojo.bo.OrderItemsCommentBO;
import com.qiyei.foodie.service.ICommentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService {

    @Override
    public List<OrderItems> queryPendingComment(String orderId) {
        return null;
    }

    @Override
    public int saveComments(String orderId, String userId, List<OrderItemsCommentBO> commentList) {
        return 0;
    }

    @Override
    public PagedGrid queryMyComments(String userId, int page, int pageSize) {
        return null;
    }
}
