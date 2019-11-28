package com.resource.admin;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.querydsl.core.Tuple;
import com.resource.admin.entity.Bookmark;
import com.resource.admin.entity.QBookmark;
import com.resource.admin.mapper.BookmarkRepository;
import com.resource.admin.service.BookmarkService;
import com.resource.admin.service.impl.BookmarkServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ResourceAdminApplicationTests {
    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Autowired
    private BookmarkService bookmarkService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void saveBookmarkTest() {
        Bookmark bookmark = new Bookmark();
        bookmark.setId(IdUtil.fastSimpleUUID());
        bookmark.setBookmarkDescription("使用QueryDSL");
        bookmark.setBookmarkUrl("https://www.jianshu.com/p/2b68af9aa0f5");
        bookmark.setCreateTime(new Date());
        bookmark.setLastUpdateTime(new Date());
        bookmark.setDeleteBool(1);
        bookmarkRepository.save(bookmark);
    }

    @Test
    public void md5Test() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = dateFormat.format(new Date());
        log.info("date: {}", dateString);
        String s = SecureUtil.md5("hb" + dateString);
        log.info("s: {}" , s);
    }

    @Test
    public void listTest() {
        ArrayList<String> list = new ArrayList<>();
        list.add("a");
        list.add("a");
        list.add("b");
        list.add("b");
        list.add("c");
        list.add("c");
        remove(list);
        log.info("list: {}", list);

    }

    private void remove(ArrayList<String> list) {
        Iterator<String> iter = list.iterator();
        while (iter.hasNext()) {
            String str = iter.next();

            if(str.equals("b")) {
                iter.remove();
            }
        }
    }

    private void removeFor(ArrayList<String> list) {
        for(String s : list ) {
            if(s.equals("b")) {
                list.remove(s);
            }
        }
    }


    @Test
    public void querydslFirstTest() {
        QBookmark qBookmark = QBookmark.bookmark;
        List<Bookmark> bookmarkList = bookmarkService.findAllByBookmarkDescriptionLike("%UI%");
        log.info("list: {}", bookmarkList);
    }

    @Test
    public void querydslSaveTest() {
        Bookmark bookmark = new Bookmark();
        final String PRIMARY_KEY = IdUtil.fastSimpleUUID();
        bookmark.setId(PRIMARY_KEY);
        bookmark.setCreateTime(new Date());
        bookmark.setLastUpdateTime(new Date());
        bookmark.setDeleteBool(1);
        bookmark.setBookmarkUrl("https://blog.csdn.net/WZH577/article/details/100877478");
        bookmark.setBookmarkDescription("SpringBoot整合QueryDSL");
        bookmarkService.save(bookmark);
    }

    @Test
    public void querydslUpdateTest() {
        final String PRIMARY_KEY = "9722d5f1eb984032a85a7bf9da0f1782";
        bookmarkService.updateDeleteBoolById(PRIMARY_KEY, 1);
    }

    @Test
    public void querydslDeleteTest() {
        final String PRIMARY_KEY = "65b1ef54ba84420d912a0d764073c0a2";
        bookmarkService.deleteById(PRIMARY_KEY);
    }

    @Test
    public void querydslTransactionTest() {
        final String PRIMARY_KEY = "e1a663695bb042f788ac6cceb1cb7799";
        bookmarkService.transactionUpdate(PRIMARY_KEY, 0, new Date());
    }

    @Test
    public void querydslOrSelectTest() {
        String condition = "dsl";
        List<Bookmark> bookmarkList = bookmarkService.getByDescriptionOrUrlContains(condition);
        log.info("list: {}", bookmarkList);
    }

    @Test
    public void querydslAndSelectTest() {
        String description = "dsl";
        String url = "csdn";
        List<Bookmark> bookmarkList = bookmarkService.getByDescriptionAndUrlContains(description, url);
        log.info("list: {}", bookmarkList);
    }

    @Test
    public void querydslMultipleSelectTest() {
        String first = "dsl";
        String url = "cdsn";
        String second = "jianshu";
        List<Bookmark> bookmarkList = bookmarkService.getByMultipleCondition(first, url, second);
        log.info("list: {}", bookmarkList);
    }

    @Test
    public void querydslSelectTest() {
        String first = "dsl";
        String url = "csdn";
        String second = "YAML";
        List<Bookmark> bookmarkList = bookmarkService.getByCondition(first, url, second);
        log.info("list: {}", bookmarkList);
    }
    @Test
    public void finallyTest() {
        finallyFunction();
        log.info("end");
    }

    @Test
    public void querydslInnerJoinTest() {
        List<Tuple> tupleList = bookmarkService.getBookmarkInnerJoin("b76949b1445d4560bec5b2e476739f1b");
        log.info("list: {}", tupleList);

    }

    private int finallyFunction() {
        try {
            log.info("outer begin");
            try {
                log.info("begin");
                int a = 1 / 0;
                return 1;
            } catch (Exception e) {
                log.info("zero1");
            } finally {
                log.info("finally");
            }
            log.info("pause");
        } catch (Exception e) {
            log.info("zero2");
        } finally {
            log.info("outer");
        }
        return 2;
    }

}
