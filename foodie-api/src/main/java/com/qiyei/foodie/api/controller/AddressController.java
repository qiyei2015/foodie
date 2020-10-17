package com.qiyei.foodie.api.controller;

import com.qiyei.common.Response;
import com.qiyei.common.utils.MobileEmailUtils;
import com.qiyei.foodie.pojo.UserAddress;
import com.qiyei.foodie.pojo.Users;
import com.qiyei.foodie.pojo.bo.AddressBO;
import com.qiyei.foodie.service.IUserAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "地址相关", tags = {"地址相关的api接口"})
@RestController
@RequestMapping("address")
public class AddressController {

    final static Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private IUserAddressService mUserAddressService;

    @ApiOperation(value = "根据用户id查询收货地址列表", notes = "根据用户id查询收货地址列表", httpMethod = "POST")
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public Response<List<UserAddress>> list(@RequestBody AddressBO addressBO) {

        if (StringUtils.isBlank(addressBO.getUserId())) {
            return Response.error("用户Id不能为空");
        }
        List<UserAddress> list = mUserAddressService.queryAll(addressBO.getUserId());
        return Response.success(list);
    }

    @ApiOperation(value = "新增地址", notes = "新增地址", httpMethod = "POST")
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public Response<String> add(@RequestBody AddressBO addressBO) {
        Response response = checkAddress(addressBO);
        if (response.isSuccess()) {
            int index = mUserAddressService.add(addressBO);
            if (index < 0) {
                return Response.error("新增地址");
            }
        }
        return response;
    }

    @ApiOperation(value = "更新地址", notes = "更新地址", httpMethod = "POST")
    @PostMapping(value = "update")
    public Response<Users> update(@RequestBody AddressBO addressBO) {
        Response response = checkAddress(addressBO);
        if (response.isSuccess()) {
            int index = mUserAddressService.update(addressBO);
            if (index < 0) {
                return Response.error("更新地址失败");
            }
        }
        return Response.success("更新地址成功");
    }

    @ApiOperation(value = "删除地址", notes = "删除地址", httpMethod = "POST")
    @PostMapping(value = "delete")
    public Response<String> delete(@RequestBody AddressBO addressBO) {
        Response response = checkAddress(addressBO);
        if (StringUtils.isBlank(addressBO.getUserId()) || StringUtils.isBlank(addressBO.getAddressId())) {
            return Response.error("用户id或地址id为空");
        }
        if (response.isSuccess()) {
            int index = mUserAddressService.delete(addressBO.getUserId(), addressBO.getAddressId());
            if (index < 0) {
                return Response.error("删除地址失败");
            }
        }
        return Response.success("删除地址成功");
    }

    @ApiOperation(value = "设置默认地址", notes = "设置默认地址", httpMethod = "POST")
    @PostMapping(value = "setDefault")
    public Response<String> setDefault(@RequestBody AddressBO addressBO) {
        Response response = checkAddress(addressBO);
        if (StringUtils.isBlank(addressBO.getUserId()) || StringUtils.isBlank(addressBO.getAddressId())) {
            return Response.error("用户id或地址id为空");
        }
        if (response.isSuccess()) {
            mUserAddressService.setDefault(addressBO.getUserId(), addressBO.getAddressId());
        }
        return Response.success("设置默认地址成功");
    }

    private Response<String> checkAddress(AddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return Response.error("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return Response.error("收货人姓名不能太长");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return Response.error("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return Response.error("收货人手机号长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return Response.error("收货人手机号格式不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return Response.error("收货地址信息不能为空");
        }

        return Response.success();
    }
}
