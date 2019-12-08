package com.resource.admin.entity.key;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
public class BookmarkTagKey implements Serializable {

    private String bookmarkId;

    private String tagId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookmarkTagKey that = (BookmarkTagKey) o;
        return Objects.equals(bookmarkId, that.bookmarkId) &&
                Objects.equals(tagId, that.tagId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookmarkId, tagId);
    }
}
