package com.resource.admin.entity.request;

import lombok.Data;

@Data
public class BasicSelectRequestBody {

    private String condition;

    private String conditionType;

    private Integer currentPageNumber;

    private Integer pageSize;
}
