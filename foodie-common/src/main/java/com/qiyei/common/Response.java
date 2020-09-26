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
    private int code;
    /**
     * 业务响应信息
     */
    private String message;
    /**
     * 响应数据
     */
    private T data;


    private Response(int code){
        this.code = code;
    }
    private Response(int code, T data){
        this.code = code;
        this.data = data;
    }

    private Response(int code, String message, T data){
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private Response(int code, String message){
        this.code = code;
        this.message = message;
    }

    @JsonIgnore
    //使之不在json序列化结果当中
    public boolean isSuccess(){
        return this.code == ResponseCode.SUCCESS.getCode();
    }

    public int getCode(){
        return code;
    }

    public T getData(){
        return data;
    }

    public String getMessage(){
        return message;
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
