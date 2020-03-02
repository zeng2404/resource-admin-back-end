package com.resource.admin.mapper;

import cn.hutool.core.util.StrUtil;
import com.querydsl.core.Tuple;
import com.querydsl.core.support.QueryBase;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.resource.admin.entity.QTag;
import com.resource.admin.entity.Tag;
import com.resource.admin.entity.response.PaginationData;
import com.resource.admin.utils.QueryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
public class TagMapper{

    private QTag qTag = QTag.tag;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private JPAQueryFactory queryFactory;

    public void saveTag(Tag tag) {
        tagRepository.save(tag);
    }

    public void update(Tag tag) {
        queryFactory.update(qTag)
                .set(Arrays.asList(qTag.lastUpdateTime, qTag.tagName, qTag.tagDescription),
                        Arrays.asList(new Date(), tag.getTagName(), tag.getTagDescription()))
                .where(qTag.id.eq(tag.getId()))
                .execute();
    }

    public void deleteTags(String[] ids) {
        queryFactory.delete(qTag)
                .where(qTag.id.in(ids))
                .execute();
    }

    public PaginationData getTags(String condition, String conditionType, Integer currentPageNumber, Integer pageSize) {
        PaginationData paginationData = new PaginationData();
        OrderSpecifier<Date> orderSpecifier = new OrderSpecifier(Order.DESC, qTag.lastUpdateTime);
        QueryBase query = queryFactory.selectFrom(qTag);
        if (StrUtil.isNotBlank(condition) &&
                StrUtil.isNotBlank(conditionType)) {
            query = query.where(qTag.tagName.containsIgnoreCase(condition)
                    .or(qTag.tagDescription.containsIgnoreCase(conditionType)));
        }

        long count = QueryUtil.fetchCountFromQueryBase(query);

        query = query.orderBy(orderSpecifier).limit(pageSize).offset(currentPageNumber * pageSize);

        List<Tag> tagList = QueryUtil.fetchFromQueryBase(query);

        paginationData.setData(tagList);
        paginationData.setTotal(count);
        return paginationData;
    }

    public Tag getTagsByTagName(String tagName) {
        return tagRepository.getTagByTagName(tagName);
    }

    public List<Tuple> getTagMenuList() {
        return queryFactory.select(qTag.id, qTag.tagName)
                .from(qTag)
                .fetch();
    }

    public boolean existsByTagName(String tagName) {
        return existsByTagName(tagName);
    }


}
