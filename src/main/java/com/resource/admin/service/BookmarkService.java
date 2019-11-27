package com.resource.admin.service;

import com.resource.admin.entity.Bookmark;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface BookmarkService {

    List<Bookmark> findAllByBookmarkDescriptionLike(String description);

    @Transactional
    void save(Bookmark bookmark);

    @Transactional
    void updateDeleteBoolById(String id, Integer deleteBool);

    @Transactional
    void deleteById(String id);

    void transactionUpdate(String id, Integer deleteBool, Date lastUpdateTime);

    List<Bookmark> getByDescriptionOrUrlContains(String condition);

    List<Bookmark> getByDescriptionAndUrlContains(String description, String url);

    List<Bookmark> getByMultipleCondition(String description, String firstUrl, String secondUrl);
}
