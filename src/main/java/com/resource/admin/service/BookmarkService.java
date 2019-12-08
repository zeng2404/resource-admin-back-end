package com.resource.admin.service;

import com.resource.admin.entity.Bookmark;
import com.resource.admin.entity.response.PaginationData;

public interface BookmarkService {

    String saveBookmark(Bookmark bookmark);

    String updateBookmark(Bookmark bookmark);

    String changeBookmarksDeleteStatus(String[] ids, Integer deleteBool);

    PaginationData getBookmarksByCondition(String condition, String conditionType,
                                           Integer currentPageNumber, Integer pageSize);

    String deleteBookmarks(String[] ids);
}
