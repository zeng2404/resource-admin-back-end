package com.resource.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.resource.admin.entity.Tag;
import com.resource.admin.entity.response.PaginationData;
import com.resource.admin.mapper.BookmarkTagMapper;
import com.resource.admin.mapper.TagMapper;
import com.resource.admin.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class TagServiceImpl implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private BookmarkTagMapper bookmarkTagMapper;


    @Override
    public String saveTag(Tag tag) {
        try {
            Tag checkTag = tagMapper.getTagsByTagName(tag.getTagName());
            if (checkTag != null) {
                return "601";
            } else {
                final String PRIMARY_KEY = IdUtil.fastSimpleUUID();
                Date now = new Date();
                tag.setLastUpdateTime(now);
                tag.setCreateTime(now);
                tag.setId(PRIMARY_KEY);
                tagMapper.saveTag(tag);
                return "200";
            }
        } catch (Exception e) {
            log.error("error: " + e);
            return "500";
        }
    }

    @Override
    public String updateTag(Tag tag) {
        try {
            Tag checkTag = tagMapper.getTagsByTagName(tag.getTagName());
            if (checkTag != null && !checkTag.getId().equals(tag.getId())) {
                return "601";
            } else {
                tagMapper.update(tag);
                return "200";
            }
        } catch (Exception e) {
            log.error("error: " + e);
            return "500";
        }
    }

    @Transactional
    @Override
    public String deleteTags(String[] ids) {
        try {
            tagMapper.deleteTags(ids);
            bookmarkTagMapper.deleteByTagIds(ids);
            return "200";
        } catch (Exception e) {
            log.error("error: " + e);
            return "500";
        }
    }

    @Override
    public PaginationData getTags(String condition, String conditionType, Integer currentPageNumber,
                                  Integer pageSize) {
        PaginationData paginationData = null;
        try {
            paginationData = tagMapper.getTags(condition, conditionType, currentPageNumber, pageSize);
            paginationData.setStatusCode("200");
        } catch (Exception e) {
            paginationData = new PaginationData();
            paginationData.setStatusCode("500");
        }
        return paginationData;
    }

    @Override
    public Map<String, Object> getTagMenuList() {
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("data", tagMapper.getTagMenuList());
            map.put("statusCode", "200");
        } catch (Exception e) {
            log.error("error: " + e);
            map.put("statusCode", "500");
        }
        return map;
    }
}
