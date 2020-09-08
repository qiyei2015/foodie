package com.qiyei.foodie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author Created by qiyei2015 on 2020/8/5.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@SpringBootApplication
// 扫描 mybatis 通用 mapper 所在的包
@MapperScan(basePackages = "com.qiyei.foodie.mapper")
//自定义包扫描
@ComponentScan(basePackages = {"com.qiyei", "org.n3r.idworker"})
public class FoodieApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodieApplication.class, args);
    }
}
