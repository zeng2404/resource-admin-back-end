package com.resource.admin.mapper;

import com.querydsl.core.types.OrderSpecifier;
import com.resource.admin.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
public interface TagRepository extends JpaRepository<Tag, String> {

    Tag getTagByTagName(String TagName);

}
