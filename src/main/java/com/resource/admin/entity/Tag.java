package com.resource.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Data;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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

    @Column(name = "delete_bool")
    private Integer deleteBool;

}
