package com.resource.admin;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.MD5;
import com.resource.admin.entity.Bookmark;
import com.resource.admin.mapper.BookmarkRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.swing.text.html.HTMLDocument;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ResourceAdminApplicationTests {
    @Autowired
    private BookmarkRepository bookmarkRepository;

    @Test
    public void contextLoads() {
    }

    @Test
    public void saveBookmarkTest() {
        Bookmark bookmark = new Bookmark();
        bookmark.setId("204dc229ca6d4d5b918529ff50a24194");
        bookmark.setBookmarkDescription("MATERIAL-UI");
        bookmark.setBookmarkUrl("https://material-ui.com/zh/");
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


}
