package com.resource.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import com.resource.admin.entity.Bookmark;
import com.resource.admin.entity.BookmarkTag;
import com.resource.admin.entity.response.PaginationData;
import com.resource.admin.mapper.BookmarkMapper;
import com.resource.admin.mapper.BookmarkTagMapper;
import com.resource.admin.service.BookmarkService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookmarkServiceImpl implements BookmarkService {

    @Autowired
    private BookmarkMapper bookmarkMapper;

    @Autowired
    private BookmarkTagMapper bookmarkTagMapper;

    @Transactional
    @Override
    public String saveBookmark(Bookmark bookmark) {
        try {
            String PRIMARY_KEY = IdUtil.fastSimpleUUID();
            Date now = new Date();
            bookmark.setId(PRIMARY_KEY);
            bookmark.setLastUpdateTime(now);
            bookmark.setCreateTime(now);
            bookmark.setDeleteBool(0);
            bookmarkMapper.saveBookmark(bookmark);
            updateBookmarkTag(PRIMARY_KEY, bookmark.getTagIds());
            return "500";
        } catch (Exception e) {
            log.error("error: " + e);
            return "500";
        }
    }

    @Transactional
    @Override
    public String updateBookmark(Bookmark bookmark) {
        try {
            bookmarkMapper.updateBookmark(bookmark);
            updateBookmarkTag(bookmark.getId(), bookmark.getTagIds());
            return "200";
        } catch (Exception e) {
            log.error("error: " + e);
            return "500";
        }
    }

    @Override
    public String changeBookmarksDeleteStatus(String[] ids, Integer deleteBool) {
        try {
            bookmarkMapper.changeBookmarksDeleteStatus(ids,deleteBool);
            return "200";
        } catch (Exception e) {
            log.error("error: " + e);
            return "500";
        }
    }

    @Override
    public PaginationData getBookmarksByCondition(String condition, String conditionType, Integer currentPageNumber, Integer pageSize) {
        PaginationData paginationData;
        try {
            paginationData = bookmarkMapper.getBookmarksByCondition(condition, conditionType,
                    currentPageNumber, pageSize);
            paginationData.setStatusCode("200");
        } catch (Exception e) {
            log.error("error: " + e);
            paginationData = new PaginationData();
            paginationData.setStatusCode("500");
        }
        return paginationData;
    }

    private void updateBookmarkTag(String bookmarkId, String[] tagIds) {
        bookmarkTagMapper.deleteByBookmarkIds(new String[]{bookmarkId});
        List<BookmarkTag> bookmarkTagList = Arrays.stream(tagIds)
                .parallel()
                .map(p -> {
                    return new BookmarkTag(bookmarkId, p);
                }).collect(Collectors.toList());
        bookmarkTagMapper.batchSave(bookmarkTagList);
    }

    @Transactional
    @Override
    public String deleteBookmarks(String[] ids) {
        try {
            bookmarkMapper.deleteBookmarks(ids);
            bookmarkTagMapper.deleteByBookmarkIds(ids);
            return "500";
        } catch (Exception e) {
            log.error("error: " + e);
            return "200";
        }
    }
}
