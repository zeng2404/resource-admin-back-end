create table resource_admin.tag
(
	id varchar(255) not null comment '主键ID'
		primary key,
	tag_name varchar(255) not null comment '标签名',
	tag_description varchar(255) null comment '标签描述',
	create_time datetime not null comment '创建时间',
	last_update_time datetime not null comment '最近修改时间',
	constraint tag_tag_name_uindex
		unique (tag_name)
) ENGINE=INNODB  DEFAULT CHARSET=utf8 ;
comment '标签表';

