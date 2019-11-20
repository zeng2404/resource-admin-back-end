package com.resource.admin.mapper;

import com.resource.admin.entity.BookmarkTag;
import com.resource.admin.entity.key.BookmarkTagKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BookmarkTagRepository extends JpaRepository<BookmarkTag, BookmarkTagKey> {

    void deleteAllByBookmarkId(String bookmarkId);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM bookmark_tag WHERE bookmark_id IN ?1")
    void deleteAllInBookmarkIds(String[] bookmarkIds);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM bookmark_tag WHERE tag_id IN ?1")
    void deleteAllByTagId(String[] tagIds);

    List<BookmarkTag> getAllByTagIdIn(String[] ids);

}
