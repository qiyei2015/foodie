package com.qiyei.foodie.service.impl;

import com.qiyei.foodie.mapper.UserAddressMapper;
import com.qiyei.foodie.pojo.UserAddress;
import com.qiyei.foodie.pojo.bo.AddressBO;
import com.qiyei.foodie.service.IUserAddressService;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author Created by qiyei2015 on 2020/9/8.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Service
public class UserAddressServiceImpl implements IUserAddressService {

    @Autowired
    private UserAddressMapper mUserAddressMapper;

    @Autowired
    private Sid sid;

    final static Logger logger = LoggerFactory.getLogger(IUserAddressService.class);

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAll(String userId) {

        Example example = new Example(UserAddress.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);

        return mUserAddressMapper.selectByExample(example);
    }

    @Override
    public UserAddress queryUserAddress(String userId, String addressId) {
        Example example = new Example(UserAddress.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",userId);
        criteria.andEqualTo("id",addressId);

        return mUserAddressMapper.selectOneByExample(example);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int add(AddressBO addressBO) {
        UserAddress userAddress = toUserAddress(addressBO);
        // TODO: 2020/9/20 需要判断地址是否重复
        return mUserAddressMapper.insert(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int update(AddressBO addressBO) {

        Example example = new Example(UserAddress.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", addressBO.getAddressId());
        UserAddress userAddress = toUserAddress(addressBO);
        return mUserAddressMapper.updateByExample(userAddress, example);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int delete(String userId, String addressId) {
        Example example = new Example(UserAddress.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", addressId);
        criteria.andEqualTo("userId", userId);

        return mUserAddressMapper.deleteByExample(example);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public int setDefault(String userId, String addressId) {

        Example example = new Example(UserAddress.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id", addressId);
        criteria.andEqualTo("userId", userId);

        UserAddress userAddress = new UserAddress();
        userAddress.setIsDefault(1);

        return mUserAddressMapper.updateByExampleSelective(userAddress, example);
    }

    private UserAddress toUserAddress(AddressBO addressBO) {
        UserAddress userAddress = new UserAddress();
        userAddress.setId(addressBO.getAddressId());
        if (StringUtils.isBlank(userAddress.getId())) {
            userAddress.setId(sid.nextShort());
        }
        userAddress.setUserId(addressBO.getUserId());
        userAddress.setReceiver(addressBO.getReceiver());
        userAddress.setMobile(addressBO.getMobile());
        userAddress.setProvince(addressBO.getProvince());
        userAddress.setCity(addressBO.getCity());
        userAddress.setDistrict(addressBO.getDistrict());
        userAddress.setDetail(addressBO.getDetail());

        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());

        return userAddress;
    }
}
