package com.resource.admin.service.impl;

import com.resource.admin.entity.Bookmark;
import com.resource.admin.entity.QBookmark;
import com.resource.admin.mapper.BookmarkRepository;
import com.resource.admin.service.BookmarkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class BookmarkServiceImpl extends BaseServiceImpl implements BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;


    public List<Bookmark> findAllByBookmarkDescriptionLike(String description) {
        QBookmark qBookmark = QBookmark.bookmark;
        return queryFactory.selectFrom(qBookmark)
                .where(qBookmark.bookmarkDescription.like(description))
                .fetch();
    }

    public void save(Bookmark bookmark) {
        entityManager.persist(bookmark);
    }

    @Override
    public void updateDeleteBoolById(String id, Integer deleteBool) {

        QBookmark qBookmark = QBookmark.bookmark;

        queryFactory.update(qBookmark)
                .set(qBookmark.deleteBool, deleteBool)
                .where(qBookmark.id.eq(id))
                .execute();
    }

    @Override
    public void deleteById(String id) {
        QBookmark qBookmark = QBookmark.bookmark;

        queryFactory.delete(qBookmark)
                .where(qBookmark.id.eq(id))
                .execute();
    }

    @Transactional
    @Override
    public void transactionUpdate(String id, Integer deleteBool, Date lastUpdateTime) {

        bookmarkRepository.changeDeleteBoolById(id, deleteBool);

        int a = 1 / 0;

        bookmarkRepository.changeLastUpdateTimeById(id, lastUpdateTime);
    }


}
