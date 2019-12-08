package com.resource.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

    @Column(name = "delete_bool")
    private Integer deleteBool;

    @Transient
    private String[] tagIds;

}
