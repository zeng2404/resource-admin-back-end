create table resource_admin.bookmark
(
	id varchar(255) not null comment '主键ID'
		primary key,
	bookmark_description varchar(255) not null comment '书签描述信息',
	bookmark_url varchar(255) not null comment '书签所在URL',
	create_time datetime not null comment '创建时间',
	last_update_time datetime not null comment '最近修改时间',
	delete_bool int default 0 not null comment '是否已删除，可恢复'
)
comment '书签表';

