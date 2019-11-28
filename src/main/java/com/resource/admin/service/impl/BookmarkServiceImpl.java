package com.resource.admin.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.resource.admin.entity.Bookmark;
import com.resource.admin.entity.QBookmark;
import com.resource.admin.mapper.BookmarkRepository;
import com.resource.admin.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManagerFactory;
import java.util.Date;
import java.util.List;

@Service
public class BookmarkServiceImpl extends BaseServiceImpl implements BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    private QBookmark qBookmark = QBookmark.bookmark;

    public List<Bookmark> findAllByBookmarkDescriptionLike(String description) {
        return queryFactory.selectFrom(qBookmark)
                .where(qBookmark.bookmarkDescription.like(description))
                .fetch();
    }

    public void save(Bookmark bookmark) {
        entityManager.persist(bookmark);
    }

    @Override
    public void updateDeleteBoolById(String id, Integer deleteBool) {
        queryFactory.update(qBookmark)
                .set(qBookmark.deleteBool, deleteBool)
                .where(qBookmark.id.eq(id))
                .execute();
    }

    @Override
    public void deleteById(String id) {
        queryFactory.delete(qBookmark)
                .where(qBookmark.id.eq(id))
                .execute();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void transactionUpdate(String id, Integer deleteBool, Date lastUpdateTime) {

        queryFactory.update(qBookmark)
                .set(qBookmark.lastUpdateTime, lastUpdateTime)
                .where(qBookmark.id.eq(id))
                .execute();

        int a = 1 / 0;

        queryFactory.update(qBookmark)
                .set(qBookmark.deleteBool, deleteBool)
                .where(qBookmark.id.eq(id))
                .execute();

    }

    public List<Bookmark> getByDescriptionOrUrlContains(String condition) {
        QBookmark qBookmark = QBookmark.bookmark;
        List<Bookmark> bookmarkList = queryFactory.selectFrom(qBookmark)
                .where(qBookmark.bookmarkDescription.containsIgnoreCase(condition)
                .or(qBookmark.bookmarkUrl.containsIgnoreCase(condition)))
                .fetch();
        return bookmarkList;
    }

    @Override
    public List<Bookmark> getByDescriptionAndUrlContains(String description, String url) {
        QBookmark qBookmark = QBookmark.bookmark;
        List<Bookmark> bookmarkList = queryFactory.selectFrom(qBookmark)
                .where(qBookmark.bookmarkDescription.containsIgnoreCase(description)
                .and(qBookmark.bookmarkUrl.containsIgnoreCase(url)))
                .fetch();
        return bookmarkList;
    }

    @Override
    public List<Bookmark> getByMultipleCondition(String description, String firstUrl, String secondUrl) {
        QBookmark qBookmark = QBookmark.bookmark;
        List<Bookmark> bookmarkList = queryFactory.selectFrom(qBookmark)
                .where(qBookmark.bookmarkDescription.containsIgnoreCase(description).and(
                        qBookmark.bookmarkUrl.containsIgnoreCase(firstUrl).or(qBookmark.bookmarkUrl.containsIgnoreCase(secondUrl))
                ))
                .fetch();
        return bookmarkList;
    }

    @Override
    public List<Bookmark> getByCondition(String firstDescription, String firstUrl, String secondDescription) {
        QBookmark qBookmark = QBookmark.bookmark;
        List<Bookmark> bookmarkList = queryFactory.selectFrom(qBookmark)
                .where(qBookmark.bookmarkDescription.containsIgnoreCase(firstDescription)
                .and(qBookmark.bookmarkUrl.containsIgnoreCase(firstUrl))
                .or(qBookmark.bookmarkDescription.containsIgnoreCase(secondDescription)))
                .fetch();
        return bookmarkList;
    }


}
