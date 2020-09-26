package com.qiyei.foodie.service.impl;

import com.qiyei.foodie.mapper.CarouselMapper;
import com.qiyei.foodie.pojo.Carousel;
import com.qiyei.foodie.service.ICarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author Created by qiyei2015 on 2020/9/26.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Service
public class CarouselServiceImpl implements ICarouselService {

    @Autowired
    private CarouselMapper mCarouselMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Carousel> queryAll(Integer show) {
        Example example = new Example(Carousel.class);
        //升序排列
        example.orderBy("sort").asc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isShow",show);
        return mCarouselMapper.selectByExample(example);
    }


}
