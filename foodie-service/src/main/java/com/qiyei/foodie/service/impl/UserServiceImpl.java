package com.qiyei.foodie.service.impl;

import com.qiyei.common.enums.Sex;
import com.qiyei.common.utils.DateUtil;
import com.qiyei.common.utils.MD5Utils;
import com.qiyei.foodie.mapper.UsersMapper;
import com.qiyei.foodie.pojo.Users;
import com.qiyei.foodie.pojo.bo.UserBO;
import com.qiyei.foodie.service.IUserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @author Created by qiyei2015 on 2020/9/8.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UsersMapper mUsersMapper;

    @Autowired
    private Sid sid;

    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

    @Override
    public Users queryUserByName(String name) {
        Example example = new Example(Users.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username",name);

        return mUsersMapper.selectOneByExample(example);
    }

    @Override
    public Users queryUser(String name, String password) {
        return null;
    }

    @Override
    public int insertUser(UserBO userBO) {
        String userId = sid.nextShort();

        Users user = new Users();
        user.setId(userId);
        user.setUsername(userBO.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 默认用户昵称同用户名
        user.setNickname(userBO.getUsername());
        // 默认头像
        user.setFace(USER_FACE);
        // 默认生日
        user.setBirthday(DateUtil.stringToDate("1900-01-01"));
        // 默认性别为 保密
        user.setSex(Sex.secret.type);

        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());

        return mUsersMapper.insert(user);
    }

    @Override
    public int updateUser(UserBO userBO) {
        return 0;
    }
}
