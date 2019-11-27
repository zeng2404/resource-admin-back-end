package com.resource.admin.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "tag")
public class Tag {

    @Id
    private String id;

    @Column(name = "tag_name")
    private String tagName;

    @Column(name ="tag_description")
    private String tagDescription;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "last_update_time")
    private Date lastUpdateTime;

}
