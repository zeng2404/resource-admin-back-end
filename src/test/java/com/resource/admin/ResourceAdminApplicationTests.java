package com.resource.admin;

import cn.hutool.core.util.IdUtil;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.SubQueryExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.resource.admin.entity.QBookmark;
import com.resource.admin.entity.QBookmarkTag;
import com.resource.admin.entity.QTag;
import com.resource.admin.entity.Tag;
import com.resource.admin.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ResourceAdminApplicationTests {

    @Autowired
    private TagService tagService;

    @Autowired
    private JPAQueryFactory queryFactory;

    @Test
    public void test() {
        Tag tag = new Tag();
        tag.setId(IdUtil.fastSimpleUUID());
        tag.setTagName("test");
        tag.setCreateTime(new Date());
        tag.setLastUpdateTime(new Date());
        tagService.saveTag(tag);
    }

    @Test
    public void groupCountTest() {
        QBookmark qBookmark = QBookmark.bookmark;
        QTag qTag = QTag.tag;
        QBookmarkTag qBookmarkTag = QBookmarkTag.bookmarkTag;
        String[] tagIds = new String[] {"0bf337202e3c4a58b0f5e71792dff6e6",
        "99fe5cd07d504933a609934c6a8af171" };
        SubQueryExpression queryExpression = queryFactory.select(qBookmarkTag.bookmarkId)
                .from(qBookmarkTag)
                .where(qBookmarkTag.tagId.in(tagIds))
                .groupBy(qBookmarkTag.bookmarkId)
                .having(qBookmarkTag.bookmarkId.count().goe(tagIds.length));
        StringExpression tagIdCollectionExpression = Expressions.stringTemplate("group_concat({0})", qTag.id).as("tagIds");
        StringExpression tagNameCollectionExpression = Expressions.stringTemplate("group_concat({0})", qTag.tagName).as("tagNames");
        List<Tuple> tupleList =  queryFactory.select(qBookmark.id,qBookmark.bookmarkDescription,qBookmark.bookmarkUrl,
                tagIdCollectionExpression, tagNameCollectionExpression)
                .from(qBookmark)
                .innerJoin(qBookmarkTag)
                .on(qBookmark.id.eq(qBookmarkTag.bookmarkId))
                .innerJoin(qTag)
                .on(qBookmarkTag.tagId.eq(qTag.id))
                .where(qBookmark.id.in(queryExpression))
                .groupBy(qBookmark.id)
                .fetch();
        log.info("list: {}", tupleList);
    }

}
