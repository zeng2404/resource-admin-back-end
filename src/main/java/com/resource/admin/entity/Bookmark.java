package com.resource.admin.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Table(name = "bookmark")
@Entity
public class Bookmark {

    @Id
    private String id;

    @Column(name = "bookmark_description")
    private String bookmarkDescription;

    @Column(name = "bookmark_url")
    private String bookmarkUrl;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    @Transient
    private String[] tagIds;

}
