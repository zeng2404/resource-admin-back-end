package com.resource.admin.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "picture")
public class Picture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pictureId;

    @Column(name = "picture_name")
    private String pictureName;

    @Column(name = "picture_description")
    private String pictureDescription;

    @Column(name = "picture_href")
    private String pictureHref;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "last_update_time")
    private Date lastUpdateTime;
}
