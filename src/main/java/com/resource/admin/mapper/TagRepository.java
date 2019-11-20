package com.resource.admin.mapper;

import com.resource.admin.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
public interface TagRepository extends JpaRepository<Tag, String>, JpaSpecificationExecutor<Tag> {

    boolean existsByTagName(String tagName);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM tag WHERE id in ?1")
    Integer deleteByIdIn(String[] ids);

}
