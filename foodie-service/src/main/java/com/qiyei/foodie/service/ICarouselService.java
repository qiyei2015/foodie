package com.qiyei.foodie.service;

import com.qiyei.foodie.pojo.Carousel;

import java.util.List;

/**
 * @author Created by qiyei2015 on 2020/9/26.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public interface ICarouselService {

    List<Carousel> queryAll(Integer show);

}
