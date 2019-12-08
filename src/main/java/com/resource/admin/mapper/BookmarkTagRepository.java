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

}
