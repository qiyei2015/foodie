package com.qiyei.foodie.service;

import com.qiyei.foodie.pojo.UserAddress;
import com.qiyei.foodie.pojo.bo.AddressBO;

import java.util.List;

/**
 * @author Created by qiyei2015 on 2020/9/8.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public interface IUserAddressService {

    List<UserAddress> queryAll(String userId);

    UserAddress queryUserAddress(String userId,String addressId);

    int add(AddressBO addressBO);

    int update(AddressBO addressBO);

    int delete(String userId, String addressId);

    int setDefault(String userId, String addressId);

}
