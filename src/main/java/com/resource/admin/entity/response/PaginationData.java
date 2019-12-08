package com.resource.admin.entity.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaginationData implements Serializable{
    // 返回的当前分页数据
    private Object data;
    // 数据总数量
    private Long total;
    // 状态码
    private String statusCode;
}
