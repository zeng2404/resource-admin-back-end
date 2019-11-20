package com.resource.admin.entity.key;


import lombok.Data;

import java.io.Serializable;

@Data
public class BookmarkTagKey implements Serializable {

    private String bookmarkId;

    private String tagId;

    @Override
    public boolean equals(Object object) {
        if(this == object){
            return true;
        }
        if(object == null){
            return false;
        }
        if(getClass() != object.getClass()){
            return false;
        }

        final BookmarkTagKey other = (BookmarkTagKey) object;

        if(bookmarkId == null){
            if(other.bookmarkId != null){
                return false;
            }
        }else if(!bookmarkId.equals(other.bookmarkId)){
            return false;
        }
        if(tagId == null){
            if(other.tagId != null){
                return false;
            }
        }else if(!tagId.equals(other.tagId)){
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((bookmarkId == null) ? 0 :bookmarkId.hashCode());
        result = PRIME * result + ((tagId == null) ? 0 :tagId.hashCode());
        return result;
    }
}
