package com.resource.admin.entity.request;

import lombok.Data;

import java.util.Date;

@Data
public class BookmarkSelectRequestBody {

    private String conditionType;

    private Integer currentPageNumber;

    private Integer pageSize;

    private String condition;

    private String[] tagIds;

    // or and
    private String tagSelectType;

}
