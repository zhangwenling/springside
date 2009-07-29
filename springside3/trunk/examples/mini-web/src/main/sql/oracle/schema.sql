
    drop table authorities cascade constraints;

    drop table resources cascade constraints;

    drop table resources_authorities cascade constraints;

    drop table roles cascade constraints;

    drop table roles_authorities cascade constraints;

    drop table users cascade constraints;

    drop table users_roles cascade constraints;

    drop sequence hibernate_sequence;

    create table authorities (
        id number(19,0) not null,
        display_name varchar2(255 char),
        name varchar2(255 char),
        primary key (id)
    );

    create table resources (
        id number(19,0) not null,
        position double precision not null,
        resource_type varchar2(255 char),
        value varchar2(255 char),
        primary key (id)
    );

    create table resources_authorities (
        resource_id number(19,0) not null,
        authority_id number(19,0) not null,
        primary key (resource_id, authority_id)
    );

    create table roles (
        id number(19,0) not null,
        name varchar2(255 char),
        primary key (id)
    );

    create table roles_authorities (
        role_id number(19,0) not null,
        authority_id number(19,0) not null,
        primary key (role_id, authority_id)
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

    alter table resources_authorities 
        add constraint FKF58B2007C67601C1 
        foreign key (authority_id) 
        references authorities;

    alter table resources_authorities 
        add constraint FKF58B200770DB5B33 
        foreign key (resource_id) 
        references resources;

    alter table roles_authorities 
        add constraint FKE9CCCC9F93BA26B3 
        foreign key (role_id) 
        references roles;

    alter table roles_authorities 
        add constraint FKE9CCCC9FC67601C1 
        foreign key (authority_id) 
        references authorities;

    alter table users_roles 
        add constraint FKF6CCD9C693BA26B3 
        foreign key (role_id) 
        references roles;

    alter table users_roles 
        add constraint FKF6CCD9C638E4EA93 
        foreign key (user_id) 
        references users;

    create sequence hibernate_sequence;
