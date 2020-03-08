package com.resource.admin.service;

import com.resource.admin.entity.Tag;
import com.resource.admin.entity.response.PaginationData;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public interface TagService {

    @Transactional
    String saveTag(Tag tag);

    @Transactional
    String updateTag(Tag tag);

    String deleteTags(String[] ids);

    PaginationData getTags(String condition, String conditionType, Integer pageSize, Integer currentPageNumber);


    Map<String, Object> getTagMenuList();


}
