package com.qiyei.foodie.service;

import com.qiyei.foodie.pojo.Users;
import com.qiyei.foodie.pojo.bo.UserBO;

/**
 * @author Created by qiyei2015 on 2020/9/8.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public interface IUserService {

    Users queryUserByName(String name);

    Users queryUser(String name,String password);

    int insertUser(UserBO userBO);

    int updateUser(UserBO userBO);
}
