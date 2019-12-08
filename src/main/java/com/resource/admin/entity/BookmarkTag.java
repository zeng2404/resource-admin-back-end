package com.resource.admin.entity;

import com.resource.admin.entity.key.BookmarkTagKey;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "bookmark_tag")
@IdClass(BookmarkTagKey.class)
public class BookmarkTag {

    @Id
    @Column(name = "bookmark_id")
    private String bookmarkId;

    @Id
    @Column(name = "tag_id")
    private String tagId;

    public BookmarkTag(String bookmarkId, String tagId) {
        this.bookmarkId = bookmarkId;
        this.tagId = tagId;
    }

    public BookmarkTag() {
    }
}
