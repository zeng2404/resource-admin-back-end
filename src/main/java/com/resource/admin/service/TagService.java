package com.resource.admin.service;

import com.resource.admin.entity.Tag;
import com.resource.admin.entity.request.BasicSelectRequestBody;
import com.resource.admin.entity.response.PaginationData;

import javax.transaction.Transactional;

public interface TagService {

    String insertTag(Tag tag);

    String updateTag(Tag tag);

    String deleteTags(String[] tagIds);

    PaginationData getTagsBySelectBody(BasicSelectRequestBody basicSelectRequestBody);
}
