package com.resource.admin.mapper;

import com.resource.admin.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TagRepository extends JpaRepository<Tag, String> {
    Tag getTagByTagName(String TagName);
}



