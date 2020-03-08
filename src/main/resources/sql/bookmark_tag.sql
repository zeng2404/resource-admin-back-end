create table resource_admin.bookmark_tag
(
	bookmark_id varchar(255) not null comment '书签表ID',
	tag_id varchar(255) not null comment '标签表ID',
	primary key (bookmark_id, tag_id)
) ENGINE=INNODB  DEFAULT CHARSET=utf8 ;
comment '书签标签关联表';

