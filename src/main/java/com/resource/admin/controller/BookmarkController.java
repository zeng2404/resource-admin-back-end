package com.resource.admin.controller;

import cn.hutool.json.JSONObject;
import com.resource.admin.entity.Bookmark;
import com.resource.admin.entity.request.BookmarkPutRequest;
import com.resource.admin.entity.request.BookmarkSelectRequestBody;
import com.resource.admin.entity.response.PaginationData;
import com.resource.admin.service.BookmarkService;
import com.resource.admin.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @PostMapping(value = "bookmark")
    public JSONObject insertBookmark(@RequestBody Bookmark bookmark) {
        JSONObject jsonObject = new JSONObject();
       String statusCode = bookmarkService.insertBookmark(bookmark);
       if("200".equals(statusCode)) {
           jsonObject = JSONResult.successResult("intl_basic_add_success");
       }
       return jsonObject;
    }

    @PutMapping(value = "bookmark")
    public JSONObject updateBookmark(@RequestBody BookmarkPutRequest bookmarkPutRequest) {
        JSONObject jsonObject = new JSONObject();
        Bookmark bookmark = bookmarkPutRequest.getBookmark();
        String handleType = bookmarkPutRequest.getHandleType();
        if("update".equals(handleType)) {
            String statusCode = bookmarkService.updateBookmark(bookmark);
            if("200".equals(statusCode)) {
                jsonObject = JSONResult.successResult("intl_basic_update_success");
            }
        }
        else if ("recovery".equals(handleType)) {
            String statusCode = bookmarkService.updateDeleteBoolStatus(
                    bookmark.getTagIds(),
                    1
            );
            jsonObject = JSONResult.successResult("intl_basic_recovery_success");
        }
        else if ("delete".equals(handleType)) {
            String statusCode = bookmarkService.updateDeleteBoolStatus(
                    bookmark.getTagIds(),
                    0
            );
            jsonObject = JSONResult.successResult("intl_basic_delete_success");
        }
        return jsonObject;
    }

    @DeleteMapping(value = "bookmark/{ids}")
    public JSONObject deleteBookmarks(@PathVariable(value = "ids") String[] ids) {
        JSONObject jsonObject = new JSONObject();
        String statusCode = bookmarkService.multipleDeleteBookmark(ids);
        if("200".equals(statusCode)) {
            jsonObject = JSONResult.successResult("intl_basic_delete_success");
        }
        return jsonObject;
    }

    @GetMapping("bookmark")
    public JSONObject getBookmarks(@RequestParam("condition") String condition,
                                   @RequestParam("conditionType") String conditionType,
                                   @RequestParam("currentPageNumber") Integer currentPageNumber,
                                   @RequestParam("pageSize") Integer pageSize,
                                   @RequestParam("tagIds") String[] tagIds,
                                   @RequestParam("tagSelectType") String tagSelectType) {
        BookmarkSelectRequestBody bookmarkSelectRequestBody = new BookmarkSelectRequestBody();
        bookmarkSelectRequestBody.setCondition(condition);
        bookmarkSelectRequestBody.setConditionType(conditionType);
        bookmarkSelectRequestBody.setCurrentPageNumber(currentPageNumber);
        bookmarkSelectRequestBody.setPageSize(pageSize);
        bookmarkSelectRequestBody.setTagIds(tagIds);
        bookmarkSelectRequestBody.setTagSelectType(tagSelectType);
        JSONObject jsonObject = new JSONObject();
        PaginationData paginationData = bookmarkService.getBookmarkListByCondition(bookmarkSelectRequestBody);
        jsonObject = JSONResult.successResult(paginationData.getData(), paginationData.getTotal());
        return jsonObject;
    }

}
