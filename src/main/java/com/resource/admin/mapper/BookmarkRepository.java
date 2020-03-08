package com.resource.admin.mapper;

import com.resource.admin.entity.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface BookmarkRepository extends JpaRepository<Bookmark, String>, JpaSpecificationExecutor<Bookmark> {

}
