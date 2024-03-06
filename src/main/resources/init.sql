create schema align;
GO

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

CREATE TABLE align.[t_follow] (
    follower_id varchar(64) not null,
    followed_id varchar(64) not null,
    PRIMARY KEY (follower_id, followed_id),
    FOREIGN KEY (follower_id) REFERENCES align.[t_user](username),
    FOREIGN KEY (followed_id) REFERENCES align.[t_user](username)
)
GO

CREATE TABLE align.[t_feeds] (
    feed_id varchar(255) PRIMARY KEY not null,
    feed_user_id varchar(64) not null,
    content varchar(MAX) COLLATE Chinese_PRC_CI_AS,
    post_time varchar(64) null,
    FOREIGN KEY (feed_user_id) REFERENCES align.[t_user](username)
)
GO