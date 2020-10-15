package com.qiyei.foodie.api.controller.center;


import com.qiyei.common.PagedGrid;
import com.qiyei.common.Response;
import com.qiyei.foodie.api.controller.BaseController;
import com.qiyei.foodie.pojo.vo.OrderStatusCountsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Api(value = "用户中心我的订单", tags = {"用户中心我的订单相关接口"})
@RestController
@RequestMapping("myorders")
public class UserOrdersController extends BaseController {


    @ApiOperation(value = "获得订单状态数概况", notes = "获得订单状态数概况", httpMethod = "POST")
    @PostMapping("/statusCounts")
    public Response statusCounts(@ApiParam(name = "userId", value = "用户id", required = true) @RequestParam String userId) {

        if (StringUtils.isBlank(userId)) {
            return Response.errorMessage("用户id为空");
        }

        OrderStatusCountsVO result = mOrdersService.getOrderStatusCounts(userId);

        return Response.success(result);
    }

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("/query")
    public Response query(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderStatus", value = "订单状态", required = false)
            @RequestParam Integer orderStatus,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(userId)) {
            return Response.errorMessage("用户id为空");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }
        PagedGrid grid = mOrdersService.queryMyOrders(userId, orderStatus, page, pageSize);
        return Response.success(grid);
    }


    // 商家发货没有后端，所以这个接口仅仅只是用于模拟
    @ApiOperation(value="商家发货", notes="商家发货", httpMethod = "GET")
    @GetMapping("/deliver")
    public Response deliver(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId) throws Exception {

        if (StringUtils.isBlank(orderId)) {
            return Response.errorMessage("订单ID不能为空");
        }
        mOrdersService.updateDeliverOrderStatus(orderId);
        return Response.success();
    }


    @ApiOperation(value="用户确认收货", notes="用户确认收货", httpMethod = "POST")
    @PostMapping("/confirmReceive")
    public Response confirmReceive(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId) throws Exception {

        Response checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getCode() != HttpStatus.OK.value()) {
            return checkResult;
        }

        boolean res = mOrdersService.updateReceiveOrderStatus(orderId);
        if (!res) {
            return Response.errorMessage("订单确认收货失败！");
        }

        return Response.success();
    }

    @ApiOperation(value="用户删除订单", notes="用户删除订单", httpMethod = "POST")
    @PostMapping("/delete")
    public Response delete(
            @ApiParam(name = "orderId", value = "订单id", required = true)
            @RequestParam String orderId,
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId) throws Exception {

        Response checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getCode() != HttpStatus.OK.value()) {
            return checkResult;
        }

        boolean res = mOrdersService.deleteOrder(userId, orderId);
        if (!res) {
            return Response.errorMessage("订单删除失败！");
        }

        return Response.success();
    }

    @ApiOperation(value = "查询订单动向", notes = "查询订单动向", httpMethod = "POST")
    @PostMapping("/trend")
    public Response trend(
            @ApiParam(name = "userId", value = "用户id", required = true)
            @RequestParam String userId,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam Integer pageSize) {

        if (StringUtils.isBlank(userId)) {
            return Response.errorMessage("用户id为空");
        }
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = COMMON_PAGE_SIZE;
        }

        PagedGrid grid = mOrdersService.getOrdersTrend(userId, page, pageSize);
        return Response.success(grid);
    }

}
