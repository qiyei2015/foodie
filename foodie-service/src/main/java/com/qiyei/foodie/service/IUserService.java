package com.qiyei.foodie.service;

import com.qiyei.foodie.pojo.Users;
import com.qiyei.foodie.pojo.bo.UserBO;
import com.qiyei.foodie.pojo.bo.UserCenterBO;
import org.apache.commons.lang3.StringUtils;

/**
 * @author Created by qiyei2015 on 2020/9/8.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public interface IUserService {

    /**
     * 查询用户
     * @param name
     * @return
     */
    Users queryUserByName(String name);

    /**
     * 查询用户
     * @param name
     * @param password
     * @return
     */
    Users queryUser(String name,String password);

    /**
     * 查询用户信息
     * @param userId
     * @return
     */
    Users queryUserInfo(String userId);

    /**
     * 插入用户
     * @param userBO
     * @return
     */
    int insertUser(UserBO userBO);

    /**
     * 更新用户信息
     * @param userBO
     * @return
     */
    int updateUser(UserBO userBO);

    /**
     * 更新用户头像到数据库
     * @param id
     * @param url
     * @return
     */
    Users updateUserFace(String id, String url);

    /**
     * 更新用户信息
     * @param id
     * @param userCenterBO
     * @return
     */
    Users updateUserInfo(String id, UserCenterBO userCenterBO);
}
