package com.qiyei.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Created by qiyei2015 on 2020/2/15.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 服务端响应
 * @JsonSerialize(include =  JsonSerialize.Inclusion.NON_NULL) 已经过时，被JsonInclude代替
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    /**
     * 业务状态码
     */
    private int status;
    /**
     * 业务响应信息
     */
    private String msg;
    /**
     * 响应数据
     */
    private T data;


    private Response(int status){
        this.status = status;
    }
    private Response(int status, T data){
        this.status = status;
        this.data = data;
    }

    private Response(int status, String msg, T data){
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    private Response(int status, String msg){
        this.status = status;
        this.msg = msg;
    }

    @JsonIgnore
    //使之不在json序列化结果当中
    public boolean isSuccess(){
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus(){
        return status;
    }

    public T getData(){
        return data;
    }

    public String getMsg(){
        return msg;
    }


    public static <T> Response<T> success(){
        return new Response<T>(ResponseCode.SUCCESS.getCode());
    }

    public static <T> Response<T> successMessage(String msg){
        return new Response<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static <T> Response<T> success(T data){
        return new Response<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static <T> Response<T> success(String msg, T data){
        return new Response<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }


    public static <T> Response<T> error(){
        return new Response<T>(ResponseCode.ERROR.getCode(),ResponseCode.ERROR.getDesc());
    }


    public static <T> Response<T> errorMessage(String errorMessage){
        return new Response<T>(ResponseCode.ERROR.getCode(),errorMessage);
    }

    public static <T> Response<T> errorCodeMessage(int errorCode, String errorMessage){
        return new Response<T>(errorCode,errorMessage);
    }


}
