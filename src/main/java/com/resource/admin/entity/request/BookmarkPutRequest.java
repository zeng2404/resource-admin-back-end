package com.resource.admin.entity.request;

import com.resource.admin.entity.Bookmark;
import lombok.Data;

@Data
public class BookmarkPutRequest {

    private String handleType;

    private Bookmark bookmark;

}
