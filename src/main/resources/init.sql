create schema align;
create table align.[t_user] (
	username varchar(64) primary key not null,
    email varchar(64) null,
    password varchar(255) not null,
    token varchar(255) not null,
    refreshToken varchar(255) not null,
    create_user_id varchar(64) NULL,
    created_time datetime NULL,
    update_user_id varchar(64) NULL,
    updated_time datetime NULL
)
GO