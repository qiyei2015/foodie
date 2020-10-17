package com.qiyei.foodie.api.controller.center;


import com.qiyei.common.PagedGrid;
import com.qiyei.common.Response;
import com.qiyei.common.enums.BooleanEnum;
import com.qiyei.foodie.api.controller.BaseController;
import com.qiyei.foodie.pojo.OrderItems;
import com.qiyei.foodie.pojo.Orders;
import com.qiyei.foodie.pojo.bo.OrderItemsCommentBO;
import com.qiyei.foodie.service.ICommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "用户中心评价模块", tags = {"用户中心评价模块相关接口"})
@RestController
@RequestMapping("comments")
public class UserCommentsController extends BaseController {

    @Autowired
    private ICommentService mCommentService;

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("/pending_items")
    public Response<List<OrderItems>> pendingItems(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId) {

        // 判断用户和订单是否关联
        Response<Orders> checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getCode() != HttpStatus.OK.value()) {
            return Response.errorMessage("用户与订单部关联");
        }
        // 判断该笔订单是否已经评价过，评价过了就不再继续
        Orders myOrder = checkResult.getData();
        if (myOrder.getIsComment() == BooleanEnum.YES.type) {
            return Response.errorMessage("该笔订单已经评价");
        }

        List<OrderItems> list = mCommentService.queryPendingComment(orderId);

        return Response.success(list);
    }


    @ApiOperation(value = "保存评论列表", notes = "保存评论列表", httpMethod = "POST")
    @PostMapping("/saveList")
    public Response<Integer> saveList(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId,
            @RequestBody List<OrderItemsCommentBO> commentList) {

        System.out.println(commentList);

        // 判断用户和订单是否关联
        Response checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getCode() != HttpStatus.OK.value()) {
            return checkResult;
        }
        // 判断评论内容list不能为空
        if (commentList == null || commentList.isEmpty() || commentList.size() == 0) {
            return Response.errorMessage("评论内容不能为空！");
        }

        if (mCommentService.saveComments(orderId, userId, commentList)){
            return Response.success();
        } else {
            return Response.errorMessage("保存失败");
        }
    }

    @ApiOperation(value = "查询我的评价", notes = "查询我的评价", httpMethod = "POST")
    @PostMapping("/query_my_comments")
    public Response queryMyComments(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(userId)) {
            return Response.errorMessage("userid为NULL");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }
        PagedGrid grid = mCommentService.queryMyComments(userId, page, pageSize);
        return Response.success(grid);
    }

}
