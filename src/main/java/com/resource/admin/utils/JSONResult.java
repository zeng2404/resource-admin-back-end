package com.resource.admin.utils;

import cn.hutool.json.JSONObject;

import java.util.Date;

public class JSONResult {
    private static final String DEFAULT_SUCCESS_MESSAGE = "请求成功";
    private static final String DEFAULT_FAIL_MESSAGE = "请求失败";

    public static JSONObject fillResultString(int status, int code, String message, Object data, long total){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", status);  // 状态(1为请求正常, 0为请求失败)
        jsonObject.put("code", code);  // 状态码
        jsonObject.put("message", message);  // 信息
        jsonObject.put("data", data);  // 数据
        jsonObject.put("total", total); // 数据总数
        jsonObject.put("time", new Date().getTime());  // 时间戳
        return jsonObject;
    }
    // 返回默认的成功请求
    public static JSONObject successResult(){
        return fillResultString(1, 200, DEFAULT_SUCCESS_MESSAGE, null,0);
    }
    public static JSONObject successResult(Object data){
        return fillResultString(1, 200, DEFAULT_SUCCESS_MESSAGE, data,0);
    }
    public static JSONObject successResult(String message){
        return fillResultString(1, 200, message, null,0);
    }
    public static JSONObject successResult(String message, Object data){
        return fillResultString(1, 200, message, data,0);
    }
    public static JSONObject successResult(Object data,long total){
        return fillResultString(1, 200, DEFAULT_SUCCESS_MESSAGE, data,total);
    }
    public static JSONObject successResult(String message,Object data,long total){
        return fillResultString(1, 200, message, data,total);
    }
    // 返回默认的失败请求
    public static  JSONObject failResult(){
        return fillResultString(0, 400, DEFAULT_FAIL_MESSAGE, null,0);
    }
    public static  JSONObject failResult(String message){
        return fillResultString(0, 400, message, null,0);
    }
    public static JSONObject failResult(String message, Integer code ){ return fillResultString(0,code,message,null,0);}
}
