package com.resource.admin.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.resource.admin.entity.Bookmark;
import com.resource.admin.entity.BookmarkTag;
import com.resource.admin.entity.request.BookmarkSelectRequestBody;
import com.resource.admin.entity.response.PaginationData;
import com.resource.admin.mapper.BookmarkRepository;
import com.resource.admin.mapper.BookmarkTagRepository;
import com.resource.admin.service.BookmarkService;
import com.resource.admin.utils.JpaUtil;
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
import java.util.stream.Collectors;

@Service
public class BookmarkServiceImpl implements BookmarkService {

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private BookmarkTagRepository bookmarkTagRepository;

    @Transactional
    @Override
    public String insertBookmark(Bookmark bookmark) {
        // inset bookmark table
        bookmark.setDeleteBool(0);
        bookmark.setCreateTime(new Date());
        bookmark.setLastUpdateTime(new Date());
        String primaryKey = IdUtil.fastSimpleUUID();
        bookmark.setId(primaryKey);
        bookmarkRepository.save(bookmark);

        // insert bookmark's tag
        List<BookmarkTag> bookmarkTagList = generateBookmarkList(
                bookmark.getTagIds(), primaryKey
        );

        if(bookmarkTagList.size() > 0) {
            bookmarkTagRepository.saveAll(bookmarkTagList);
        }

        // TODO catch exception

        return "200";
    }

    @Transactional
    @Override
    public String updateBookmark(Bookmark bookmark) {
        // update bookmark table
        Optional<Bookmark> bookmarkOptional = bookmarkRepository.findById(bookmark.getId());
        bookmark.setLastUpdateTime(new Date());
        JpaUtil.copyNotNullProperties(bookmark, bookmarkOptional.get());
        if(bookmarkOptional.isPresent()) {
            bookmarkRepository.save(bookmarkOptional.get());
            // delete bookmark_tag table
            bookmarkTagRepository.deleteAllByBookmarkId(bookmark.getId());
            // insert bookmark's tag again
            List<BookmarkTag> bookmarkTagList = generateBookmarkList(
                    bookmark.getTagIds(), bookmark.getId()
            );
            if(bookmarkTagList.size() > 0) {
                bookmarkTagRepository.saveAll(bookmarkTagList);
            }
        }
        else {
            return "601";
        }
        // TODO catch exception
        return "200";
    }

    @Override
    public String updateDeleteBoolStatus(String[] ids, Integer deleteBool) {
        bookmarkRepository.changeBookmarksStatus(ids, deleteBool);

        // TODO catch exception

        return "200";
    }

    @Transactional
    @Override
    public String multipleDeleteBookmark(String[] bookmarkIds) {
        // delete bookmark
        bookmarkRepository.deleteAllInBookmarkIds(bookmarkIds);

        // delete bookmark_tag
        bookmarkTagRepository.deleteAllInBookmarkIds(bookmarkIds);

        // TODO catch exception

        return "200";
    }

    @Override
    public PaginationData getBookmarkListByCondition(BookmarkSelectRequestBody bookmarkSelectRequestBody) {
        PaginationData paginationData = new PaginationData();
        String conditionType = bookmarkSelectRequestBody.getConditionType().trim();
        String condition = bookmarkSelectRequestBody.getCondition().trim();
        Integer currentPageNumber = bookmarkSelectRequestBody.getCurrentPageNumber();
        Integer pageSize = bookmarkSelectRequestBody.getPageSize();
        if("tagIds".equals(conditionType)) {
            String tagSelectType = bookmarkSelectRequestBody.getTagSelectType();
            if("or".equals(tagSelectType)) {
                paginationData.setTotal(bookmarkRepository.countBookmarksWithTag(
                        bookmarkSelectRequestBody.getTagIds()));
                paginationData.setData(bookmarkRepository.getBookmarksWithTag(
                        bookmarkSelectRequestBody.getTagIds(),
                        pageSize,
                        currentPageNumber * pageSize
                ));
            }
            else if("and".equals(tagSelectType)) {
                paginationData.setTotal(bookmarkRepository.countBookmarksWithMultipleTagCount(
                        bookmarkSelectRequestBody.getTagIds(),
                        bookmarkSelectRequestBody.getTagIds().length
                ));
                paginationData.setData(bookmarkRepository.getBookmarksWithMultipleTag(
                        bookmarkSelectRequestBody.getTagIds(),
                        bookmarkSelectRequestBody.getTagIds().length,
                        pageSize,
                        currentPageNumber * pageSize
                ));
            }
        }
        else {
            Sort sort = Sort.by(Sort.Direction.DESC,"lastUpdateTime");
            Specification specification = new Specification() {
                @Override
                public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    Predicate[] predicates = new Predicate[2];
                    if(!StrUtil.isNullOrUndefined(condition)) {
                        if("description".equals(conditionType)) {
                            predicates[0] = criteriaBuilder.like(root.get("bookmarkDescription").as(String.class),
                                    "%" + condition + "%");
                        }
                    }
                    predicates[1] = criteriaBuilder.equal(root.get("deleteBool").as(Integer.class),
                            0);
                    return criteriaBuilder.and(predicates);
                }
            };
            Pageable pageable = PageRequest.of(
                    bookmarkSelectRequestBody.getCurrentPageNumber(),
                    bookmarkSelectRequestBody.getPageSize(),
                    sort
            );
            Page<Bookmark> bookmarkPage = bookmarkRepository.findAll(specification, pageable);
            paginationData.setTotal(bookmarkPage.getTotalElements());
            paginationData.setData(bookmarkPage.getContent());
        }
        return paginationData;
    }


    private List<BookmarkTag> generateBookmarkList(String[] tagIds, String bookmarkId) {
        List<BookmarkTag> bookmarkTagList = Arrays.stream(tagIds).map(
                p -> {
                    BookmarkTag bookmarkTag = new BookmarkTag();
                    bookmarkTag.setBookmarkId(bookmarkId);
                    bookmarkTag.setTagId(p);
                    return bookmarkTag;
                }
        ).collect(Collectors.toList());
        return bookmarkTagList;
    }
}
