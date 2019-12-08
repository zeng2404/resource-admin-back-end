package com.resource.admin.utils;

import cn.hutool.json.JSONObject;

import java.util.Date;

public class JSONResult {
    public static final JSONObject SUCCESS_RESULT = JSONResult.of(200);
    public static final JSONObject FAIL_RESULT = JSONResult.of(500);

    public static JSONObject valueOf(Integer statusCode, String message, Object data, long total){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("statusCode", statusCode);  // 状态码
        jsonObject.put("message", message);  // 信息
        jsonObject.put("data", data);  // 数据
        jsonObject.put("total", total); // 数据总数
        return jsonObject;
    }

    public static JSONObject of(Integer statusCode) {
        return valueOf(statusCode, null, null, 0);
    }

    public static JSONObject of(Integer statusCode, String message) {
        return valueOf(statusCode, message, null, 0);
    }

    public static JSONObject of(Integer statusCode, String message, Object data) {
        return valueOf(statusCode, message, data, 0);
    }

    public static JSONObject fromPagination(Object data, long total) {
        return valueOf(200, null, data, total);
    }





}
