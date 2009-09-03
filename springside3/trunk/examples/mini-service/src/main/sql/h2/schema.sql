
    alter table ss_user_role 
        drop constraint FK1306854B6B2AC727;

    alter table ss_user_role 
        drop constraint FK1306854BC6000347;

    drop table ss_role if exists;

    drop table ss_user if exists;

    drop table ss_user_role if exists;

    create table ss_role (
        id bigint generated by default as identity,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table ss_user (
        id bigint generated by default as identity,
        email varchar(255),
        login_name varchar(255) not null unique,
        name varchar(255),
        password varchar(255),
        primary key (id)
    );

    create table ss_user_role (
        user_id bigint not null,
        role_id bigint not null,
        primary key (user_id, role_id)
    );

    alter table ss_user_role 
        add constraint FK1306854B6B2AC727 
        foreign key (user_id) 
        references ss_user;

    alter table ss_user_role 
        add constraint FK1306854BC6000347 
        foreign key (role_id) 
        references ss_role;
