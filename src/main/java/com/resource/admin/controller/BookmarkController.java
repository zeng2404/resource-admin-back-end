package com.resource.admin.controller;

import cn.hutool.json.JSONObject;
import com.resource.admin.entity.Bookmark;
import com.resource.admin.entity.request.BookmarkPutRequest;
import com.resource.admin.entity.response.PaginationData;
import com.resource.admin.service.BookmarkService;
import com.resource.admin.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @GetMapping(value = "bookmark")
    public JSONObject selectBookmarks(@RequestParam(value = "condition") String condition,
                                      @RequestParam(value = "conditionType") String conditionType,
                                      @RequestParam(value = "currentPageNumber") Integer currentPageNumber,
                                      @RequestParam(value = "pageSize") Integer pageSize,
                                      @RequestParam(value = "selectType") String selectType) {
        JSONObject jsonObject = null;
        if ("selectBookmarks".equals(selectType)) {
            PaginationData paginationData = bookmarkService.getBookmarksByCondition(condition, conditionType,
                    currentPageNumber, pageSize);
            String statusCode = paginationData.getStatusCode();
            if ("200".equals(statusCode)) {
                jsonObject = JSONResult.fromPagination(paginationData.getData(), paginationData.getTotal());
            } else {
                jsonObject = JSONResult.FAIL_RESULT;
            }
        }
        return jsonObject;
    }

    @PostMapping(value = "bookmark")
    public JSONObject saveBookmark(@RequestBody Bookmark bookmark) {
        String statusCode = bookmarkService.saveBookmark(bookmark);
        if ("200".equals(statusCode)) {
            return JSONResult.SUCCESS_RESULT;
        } else {
            return JSONResult.FAIL_RESULT;
        }
    }

    @PutMapping(value = "bookmark/{ids}")
    public JSONObject handleBookmark(@PathVariable(value = "ids") String[] ids,
                                     @RequestBody BookmarkPutRequest bookmarkPutRequest) {
        String handleType = bookmarkPutRequest.getHandleType();
        if ("updateBookmark".equals(handleType)) {
            return updateBookmark(bookmarkPutRequest.getBookmark());
        }
        return null;
    }

    @DeleteMapping(value = "bookmark/{ids}")
    public JSONObject deleteBookmarks(@PathVariable(value = "ids") String[] ids) {
        String statusCode = bookmarkService.deleteBookmarks(ids);
        if ("200".equals(statusCode)) {
            return JSONResult.SUCCESS_RESULT;
        } else {
            return JSONResult.FAIL_RESULT;
        }
    }

    private JSONObject updateBookmark(Bookmark bookmark) {
        String statusCode = bookmarkService.updateBookmark(bookmark);
        if ("200".equals(statusCode)) {
            return JSONResult.SUCCESS_RESULT;
        } else {
            return JSONResult.FAIL_RESULT;
        }
    }

}
