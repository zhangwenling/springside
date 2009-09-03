
    alter table ss_post 
        drop constraint FK8D193F1F7334D076;

    alter table ss_post 
        drop constraint FK8D193F1FF125651E;

    alter table ss_user_role 
        drop constraint FK1306854BF125651E;

    alter table ss_user_role 
        drop constraint FK1306854B4BFAA13E;

    drop table ss_post if exists;

    drop table ss_role if exists;

    drop table ss_user if exists;

    drop table ss_user_role if exists;

    create table ss_post (
        dtype varchar(31) not null,
        id bigint generated by default as identity,
        content clob,
        modify_time timestamp,
        title varchar(255) not null,
        user_id bigint,
        subject_id bigint,
        primary key (id)
    );

    create table ss_role (
        id bigint generated by default as identity,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table ss_user (
        id bigint generated by default as identity,
        create_by varchar(255),
        create_time timestamp,
        last_modify_by varchar(255),
        last_modify_time timestamp,
        email varchar(255),
        login_name varchar(255) not null unique,
        name varchar(255),
        plain_password varchar(255),
        sha_password varchar(255),
        status varchar(255),
        version integer,
        primary key (id)
    );

    create table ss_user_role (
        user_id bigint not null,
        role_id bigint not null,
        primary key (user_id, role_id)
    );

    alter table ss_post 
        add constraint FK8D193F1F7334D076 
        foreign key (subject_id) 
        references ss_post;

    alter table ss_post 
        add constraint FK8D193F1FF125651E 
        foreign key (user_id) 
        references ss_user;

    alter table ss_user_role 
        add constraint FK1306854BF125651E 
        foreign key (user_id) 
        references ss_user;

    alter table ss_user_role 
        add constraint FK1306854B4BFAA13E 
        foreign key (role_id) 
        references ss_role;
