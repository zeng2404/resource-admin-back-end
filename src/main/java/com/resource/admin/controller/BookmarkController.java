package com.resource.admin.controller;

import com.resource.admin.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class BookmarkController {

    @Autowired
    private BookmarkService bookmarkService;

    @GetMapping("test")
    public void test() {
        final String PRIMARY_KEY = "e1a663695bb042f788ac6cceb1cb7799";
        bookmarkService.transactionUpdate(PRIMARY_KEY, 0, new Date());
    }
}
