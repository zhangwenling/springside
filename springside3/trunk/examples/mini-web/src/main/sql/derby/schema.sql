
    alter table ss_resource_authority 
        drop constraint FKD7216891C67601C1;

    alter table ss_resource_authority 
        drop constraint FKD721689170DB5B33;

    alter table ss_role_authority 
        drop constraint FKE536BA7993BA26B3;

    alter table ss_role_authority 
        drop constraint FKE536BA79C67601C1;

    alter table ss_user_role 
        drop constraint FK1306854B38E4EA93;

    alter table ss_user_role 
        drop constraint FK1306854B93BA26B3;

    drop table ss_authority;

    drop table ss_resource;

    drop table ss_resource_authority;

    drop table ss_role;

    drop table ss_role_authority;

    drop table ss_user;

    drop table ss_user_role;

    create table ss_authority (
        id bigint not null generated always as identity,
        display_name varchar(255),
        name varchar(255) not null unique,
        primary key (id)
    );

    create table ss_resource (
        id bigint not null generated always as identity,
        position double not null,
        resource_type varchar(255) not null,
        value varchar(255) not null unique,
        primary key (id)
    );

    create table ss_resource_authority (
        resource_id bigint not null,
        authority_id bigint not null,
        primary key (resource_id, authority_id)
    );

    create table ss_role (
        id bigint not null generated always as identity,
        name varchar(255) not null unique,
        primary key (id)
    );

    create table ss_role_authority (
        role_id bigint not null,
        authority_id bigint not null,
        primary key (role_id, authority_id)
    );

    create table ss_user (
        id bigint not null generated always as identity,
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

    alter table ss_resource_authority 
        add constraint FKD7216891C67601C1 
        foreign key (authority_id) 
        references ss_authority;

    alter table ss_resource_authority 
        add constraint FKD721689170DB5B33 
        foreign key (resource_id) 
        references ss_resource;

    alter table ss_role_authority 
        add constraint FKE536BA7993BA26B3 
        foreign key (role_id) 
        references ss_role;

    alter table ss_role_authority 
        add constraint FKE536BA79C67601C1 
        foreign key (authority_id) 
        references ss_authority;

    alter table ss_user_role 
        add constraint FK1306854B38E4EA93 
        foreign key (user_id) 
        references ss_user;

    alter table ss_user_role 
        add constraint FK1306854B93BA26B3 
        foreign key (role_id) 
        references ss_role;
