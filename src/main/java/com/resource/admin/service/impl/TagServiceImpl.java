package com.resource.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.resource.admin.entity.Tag;
import com.resource.admin.entity.request.BasicSelectRequestBody;
import com.resource.admin.entity.response.PaginationData;
import com.resource.admin.mapper.BookmarkTagRepository;
import com.resource.admin.mapper.TagRepository;
import com.resource.admin.service.TagService;
import com.resource.admin.utils.JpaUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private BookmarkTagRepository bookmarkTagRepository;

    @Override
    public String insertTag(Tag tag) {
        String primaryKey = IdUtil.fastSimpleUUID();
        tag.setCreateTime(new Date());
        tag.setLastUpdateTime(new Date());
        tag.setId(primaryKey);
        if(tagRepository.existsByTagName(tag.getTagName())) {
            log.info("tag should be unique");
            return "601";
        }
        else {
            tagRepository.save(tag);
            return "200";
        }
    }

    @Override
    public String updateTag(Tag tag) {
        Optional<Tag> tagOption = tagRepository.findById(tag.getId());
        if(tagOption.isPresent() &&
                !tagOption.get().getTagName().equals(tag.getTagName())
        && tagRepository.existsByTagName(tag.getTagName())) {
            log.info("tag name should be unique");
            return "601";
        }
        else {
            tag.setLastUpdateTime(new Date());
            JpaUtil.copyNotNullProperties(tag, tagOption.get());
            tagRepository.save(tagOption.get());
            return "200";
        }
    }

    @Transactional
    @Override
    public String deleteTags(String[] tagIds) {
        tagRepository.deleteByIdIn(tagIds);
        bookmarkTagRepository.deleteAllByTagId(tagIds);
        return "200";
    }

    @Override
    public PaginationData getTagsBySelectBody(BasicSelectRequestBody basicSelectRequestBody) {
        PaginationData paginationData = new PaginationData();
        String condition = basicSelectRequestBody.getCondition();

        Sort sort = Sort.by(Sort.Direction.DESC, "lastUpdateTime");

        Specification specification = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate[] predicates = new Predicate[2];
                predicates[0] = criteriaBuilder.like(root.get("tagName").as(String.class), "%" + condition + "%");
                predicates[1] = criteriaBuilder.like(root.get("tagDescription").as(String.class), "%" + condition + "%");
                return criteriaBuilder.or(predicates);
            }
        };

        Pageable pageable = PageRequest.of(
                basicSelectRequestBody.getCurrentPageNumber(),
                basicSelectRequestBody.getPageSize(),
                sort
        );

        Page<Tag> tagPage;
        if(StrUtil.isEmptyOrUndefined(condition)) {
            tagPage = tagRepository.findAll(pageable);
        }
        else {
            tagPage = tagRepository.findAll(specification, pageable);
        }
        paginationData.setData(tagPage.getContent());
        paginationData.setTotal(tagPage.getTotalElements());
        return paginationData;
    }

}
