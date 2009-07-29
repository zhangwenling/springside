
    drop table roles cascade constraints;

    drop table users cascade constraints;

    drop table users_roles cascade constraints;

    drop sequence hibernate_sequence;

    create table roles (
        id number(19,0) not null,
        name varchar2(255 char),
        primary key (id)
    );

    create table users (
        id number(19,0) not null,
        email varchar2(255 char),
        login_name varchar2(255 char),
        name varchar2(255 char),
        password varchar2(255 char),
        primary key (id)
    );

    create table users_roles (
        user_id number(19,0) not null,
        role_id number(19,0) not null,
        primary key (user_id, role_id)
    );

    alter table users_roles 
        add constraint FKF6CCD9C6C6000347 
        foreign key (role_id) 
        references roles;

    alter table users_roles 
        add constraint FKF6CCD9C66B2AC727 
        foreign key (user_id) 
        references users;

    create sequence hibernate_sequence;
