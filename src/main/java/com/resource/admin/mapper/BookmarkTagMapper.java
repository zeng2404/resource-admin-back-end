package com.resource.admin.mapper;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.resource.admin.entity.BookmarkTag;
import com.resource.admin.entity.QBookmarkTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookmarkTagMapper {

    @Autowired
    private BookmarkTagRepository bookmarkTagRepository;

    @Autowired
    private JPAQueryFactory queryFactory;

    private QBookmarkTag qBookmarkTag = QBookmarkTag.bookmarkTag;

    public void deleteByTagIds(String[] tagIds) {
        queryFactory.delete(qBookmarkTag)
                .where(qBookmarkTag.tagId.in(tagIds))
                .execute();
    }

    public void batchSave(List<BookmarkTag> bookmarkTagList) {
        bookmarkTagRepository.saveAll(bookmarkTagList);
    }

    public void deleteByBookmarkIds(String[] bookmarkIds) {
        queryFactory.delete(qBookmarkTag)
                .where(qBookmarkTag.bookmarkId.in(bookmarkIds))
                .execute();
    }
}
