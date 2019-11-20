package com.resource.admin.controller;

import cn.hutool.json.JSONObject;
import com.resource.admin.entity.Tag;
import com.resource.admin.entity.request.BasicSelectRequestBody;
import com.resource.admin.entity.response.PaginationData;
import com.resource.admin.service.TagService;
import com.resource.admin.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping(value = "tag")
    public JSONObject addTag(@RequestBody Tag tag) {
        String statusCode = tagService.insertTag(tag);
        JSONObject jsonObject = new JSONObject();
        if("601".equals(statusCode)) {
            jsonObject = JSONResult.failResult("intl_basic_add_fail", 601);
        }
        else if("200".equals(statusCode)) {
            jsonObject =JSONResult.successResult("intl_basic_add_success");
        }
        return jsonObject;
    }

    @PutMapping(value = "tag/{id}")
    public JSONObject updateTag(@PathVariable(value = "id") String id,
                                @RequestBody Tag tag) {
        tag.setId(id);
        JSONObject jsonObject = new JSONObject();
        String statusCode = tagService.updateTag(tag);
        if("200".equals(statusCode)) {
            jsonObject = JSONResult.successResult("intl_basic_update_success");
        }
        else if("601".equals(statusCode)) {
            jsonObject = JSONResult.failResult("intl_basic_update_fail", 601);
        }
        return jsonObject;
    }

    @DeleteMapping(value = "tag/{ids}")
    public JSONObject deleteTag(@PathVariable(value = "ids") String[] ids) {
        JSONObject jsonObject = new JSONObject();
        String statusCode = tagService.deleteTags(ids);
        if("200".equals(statusCode)) {
            jsonObject = JSONResult.successResult("intl_basic_delete_success");
        }
        return jsonObject;
    }

    @GetMapping(value = "tag")
    public JSONObject getTag(@RequestParam("condition") String condition,
                             @RequestParam("currentPageNumber") Integer currentPageNumber,
                             @RequestParam("pageSize") Integer pageSize) {
        BasicSelectRequestBody basicSelectRequestBody = new BasicSelectRequestBody();
        basicSelectRequestBody.setCondition(condition);
        basicSelectRequestBody.setCurrentPageNumber(currentPageNumber);
        basicSelectRequestBody.setPageSize(pageSize);
        PaginationData paginationData = tagService.getTagsBySelectBody(basicSelectRequestBody);
        JSONObject jsonObject = JSONResult.successResult(paginationData.getData(), paginationData.getTotal());
        return jsonObject;
    }


}
