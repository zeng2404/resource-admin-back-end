package com.resource.admin.controller;

import cn.hutool.json.JSONObject;
import com.resource.admin.entity.Tag;
import com.resource.admin.entity.request.TagPutRequest;
import com.resource.admin.entity.response.PaginationData;
import com.resource.admin.service.TagService;
import com.resource.admin.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping(value = "tag")
    public JSONObject getTag(@RequestParam(value = "condition") String condition,
                             @RequestParam(value = "conditionType") String conditionType,
                             @RequestParam(value = "pageSize") Integer pageSize,
                             @RequestParam(value = "currentPageNumber") Integer currentPageNumber,
                             @RequestParam(value = "selectType") String selectType) {
        JSONObject jsonObject = null;
        if("getTagMenuList".equals(selectType)) {
           jsonObject = getTagMenuList();
        }
        else if("getTagsByCondition".equals(selectType)) {
            jsonObject = getTagsByCondition(condition, conditionType,
                    currentPageNumber, pageSize);
        }
        return jsonObject;
    }

    @PostMapping(value = "tag")
    public JSONObject saveTag(@RequestBody Tag tag) {
        String statusCode = tagService.saveTag(tag);
        if ("200".equals(statusCode)) {
            return JSONResult.SUCCESS_RESULT;
        } else if ("601".equals(statusCode)) {
            return JSONResult.of(601);
        } else {
            return JSONResult.FAIL_RESULT;
        }
    }

    @PutMapping(value = "tag/{ids}")
    public JSONObject handleTag(@PathVariable(value = "ids") String[] ids, @RequestBody TagPutRequest tagPutRequest) {
        JSONObject jsonObject = null;
        if ("updateTag".equals(tagPutRequest.getHandleType())) {
            jsonObject = updateTag(tagPutRequest.getTag());
        }
        else if("updateDeleteStatus".equals(tagPutRequest.getHandleType())) {
            jsonObject = updateTagDeleteStatus(ids, tagPutRequest.getTag().getDeleteBool());
        }
        return jsonObject;
    }

    @DeleteMapping(value = "tag/{ids}")
    public JSONObject deleteTags(@PathVariable(value = "ids") String[] ids) {
        String statusCode = tagService.deleteTags(ids);
        if ("200".equals(statusCode)) {
            return JSONResult.SUCCESS_RESULT;
        } else {
            return JSONResult.FAIL_RESULT;
        }
    }

    private JSONObject updateTag(Tag tag) {
        String statusCode = tagService.updateTag(tag);
        if ("601".equals(statusCode)) {
            return JSONResult.of(601, "tag 重复");
        } else if ("200".equals(statusCode)) {
            return JSONResult.SUCCESS_RESULT;
        } else {
            return JSONResult.FAIL_RESULT;
        }
    }

    private JSONObject getTagsByCondition(String condition, String conditionType,
                                          Integer currentPageNumber, Integer pageSize) {
        PaginationData paginationData = tagService.getTags(condition, conditionType, pageSize, currentPageNumber);
        if ("200".equals(paginationData.getStatusCode())) {
            return JSONResult.fromPagination(paginationData.getData(), paginationData.getTotal());
        } else {
            return JSONResult.FAIL_RESULT;
        }
    }

    private JSONObject getTagMenuList() {
        Map<String, Object> map = tagService.getTagMenuList();
        String statusCode = map.get("statusCode").toString();
        if("200".equals(statusCode)) {
            return JSONResult.of(200, "", map.get("data"));
        }
        else {
            return JSONResult.FAIL_RESULT;
        }
    }

    private JSONObject updateTagDeleteStatus(String[] ids, Integer deleteBool) {
        String statusCode = tagService.changeTagsDeleteStatus(ids, deleteBool);
        if ("200".equals(statusCode)) {
            return JSONResult.SUCCESS_RESULT;
        }
        else {
            return JSONResult.SUCCESS_RESULT;
        }
    }
}
