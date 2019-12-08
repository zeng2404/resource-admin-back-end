package com.resource.admin.entity.request;

import com.resource.admin.entity.Tag;
import lombok.Data;

@Data
public class TagPutRequest {
    private Tag tag;

    private String handleType;
}
