package com.resource.admin.service;

import com.resource.admin.entity.Bookmark;
import com.resource.admin.entity.request.BookmarkSelectRequestBody;
import com.resource.admin.entity.response.PaginationData;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface BookmarkService {

    String insertBookmark(Bookmark bookmark);

    String updateBookmark(Bookmark bookmark);

    String updateDeleteBoolStatus(String[] ids, Integer deleteBool);

    String multipleDeleteBookmark(String[] bookmarkIds);

    PaginationData getBookmarkListByCondition(BookmarkSelectRequestBody bookmarkSelectRequestBody);


}
