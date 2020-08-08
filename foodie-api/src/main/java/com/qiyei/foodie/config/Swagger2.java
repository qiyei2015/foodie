package com.qiyei.foodie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Created by qiyei2015 on 2020/8/8.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.qiyei.foodie.api.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo  apiInfo(){
        return new ApiInfoBuilder()
                .title("美食家")
                .contact(new Contact("qiyei2009","https://github.com/qiyei2015/foodie","1273482124@qq.com"))
                .description("专为美食家提供的api文档")
                .version("1.0.0")
                .termsOfServiceUrl("https://github.com/qiyei2015/foodie")
                .build();
    }
}
