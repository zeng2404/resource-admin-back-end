package com.resource.admin.mapper;

import cn.hutool.core.util.StrUtil;
import com.querydsl.core.Tuple;
import com.querydsl.core.support.QueryBase;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.resource.admin.entity.Bookmark;
import com.resource.admin.entity.QBookmark;
import com.resource.admin.entity.QBookmarkTag;
import com.resource.admin.entity.QTag;
import com.resource.admin.entity.response.PaginationData;
import com.resource.admin.utils.MapUtil;
import com.resource.admin.utils.QueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Repository
public class BookmarkMapper {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private JPAQueryFactory queryFactory;

    private QBookmark qBookmark = QBookmark.bookmark;

    private QTag qTag = QTag.tag;

    private QBookmarkTag qBookmarkTag = QBookmarkTag.bookmarkTag;

    public void saveBookmark(Bookmark bookmark) {
        bookmarkRepository.save(bookmark);
    }

    public void deleteBookmarks(String[] bookmarkIds) {
        queryFactory.delete(qBookmark)
                .where(qBookmark.id.in(bookmarkIds))
                .execute();
    }

    public void updateBookmark(Bookmark bookmark) {
        queryFactory.update(qBookmark)
                .set(qBookmark.bookmarkDescription, bookmark.getBookmarkDescription())
                .set(qBookmark.bookmarkUrl, bookmark.getBookmarkUrl())
                .set(qBookmark.lastUpdateTime, new Date())
                .where(qBookmark.id.eq(bookmark.getId()))
                .execute();
    }

    public PaginationData getBookmarksByCondition(String condition, String conditionType,
                                                  Integer currentPageNumber, Integer pageSize) {
        PaginationData paginationData = new PaginationData();
        OrderSpecifier<Date> orderSpecifier = new OrderSpecifier(Order.DESC, qBookmark.lastUpdateTime);
        StringExpression tagIdCollectionExpression = Expressions.stringTemplate("group_concat({0})", qTag.id).as("tagIds");
        StringExpression tagNameCollectionExpression = Expressions.stringTemplate("group_concat({0})", qTag.tagName).as("tagNames");
        QueryBase query = queryFactory.select(qBookmark.id, qBookmark.bookmarkDescription, qBookmark.bookmarkUrl,
                qBookmark.createTime, qBookmark.lastUpdateTime, tagIdCollectionExpression, tagNameCollectionExpression)
                .from(qBookmark)
                .innerJoin(qBookmarkTag)
                .on(qBookmark.id.eq(qBookmarkTag.bookmarkId))
                .innerJoin(qTag)
                .on(qTag.id.eq(qBookmarkTag.tagId));
        if (StrUtil.isNotBlank(condition) && StrUtil.isNotBlank(conditionType)) {
            if ("tagId-and".equals(conditionType)) {
                String[] tagIds = condition.split(",");
                SubQueryExpression queryExpression = queryFactory.select(qBookmarkTag.bookmarkId)
                        .from(qBookmarkTag)
                        .where(qBookmarkTag.tagId.in(tagIds))
                        .groupBy(qBookmarkTag.bookmarkId)
                        .having(qBookmarkTag.bookmarkId.count().goe(tagIds.length));
                query = query.where(qBookmark.id.in(queryExpression));
            } else if ("tagId-or".equals(conditionType)) {
                query = query.where(qTag.id.in(condition.split(",")));
            } else {
                query = query.where(qBookmark.bookmarkDescription.containsIgnoreCase(condition)
                        .or(qBookmark.bookmarkUrl.containsIgnoreCase(condition)));
            }
        }
        query = query.groupBy(qBookmark.id);
        long count = QueryUtil.fetchCountFromQueryBase(query);
        query = query.orderBy(orderSpecifier).limit(pageSize).offset(currentPageNumber * pageSize);
        List<Tuple> tupleList = QueryUtil.fetchFromQueryBase(query);
        List<String> keyList = Arrays.asList("bookmarkId", "bookmarkDescription", "bookmarkUrl",
                "createTime", "lastUpdateTime", "tagIds", "tagNames");
        paginationData.setTotal(count);
        paginationData.setData(MapUtil.fromTuple(tupleList, keyList));
        return paginationData;
    }
}
