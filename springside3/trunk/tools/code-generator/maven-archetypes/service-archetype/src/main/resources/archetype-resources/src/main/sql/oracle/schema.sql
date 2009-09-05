
    drop table ss_role cascade constraints;

    drop table ss_user cascade constraints;

    drop table ss_user_role cascade constraints;

    drop sequence hibernate_sequence;

    create table ss_role (
        id number(19,0) not null,
        name varchar2(255 char) not null unique,
        primary key (id)
    );

    create table ss_user (
        id number(19,0) not null,
        email varchar2(255 char),
        login_name varchar2(255 char) not null unique,
        name varchar2(255 char),
        password varchar2(255 char),
        primary key (id)
    );

    create table ss_user_role (
        user_id number(19,0) not null,
        role_id number(19,0) not null
    );

    alter table ss_user_role 
        add constraint FK1306854B6B2AC727 
        foreign key (user_id) 
        references ss_user;

    alter table ss_user_role 
        add constraint FK1306854BC6000347 
        foreign key (role_id) 
        references ss_role;

    create sequence hibernate_sequence;
