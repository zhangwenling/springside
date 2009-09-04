    drop table if exists ss_user_role;
    
    drop table if exists ss_role;

    drop table if exists ss_user;

    create table ss_role (
        id bigint not null auto_increment,
        name varchar(255) not null unique,
        primary key (id)
    ) ENGINE=InnoDB;

    create table ss_user (
        id bigint not null auto_increment,
        email varchar(255),
        login_name varchar(255) not null unique,
        name varchar(255),
        password varchar(255),
        primary key (id)
    ) ENGINE=InnoDB;

    create table ss_user_role (
        user_id bigint not null,
        role_id bigint not null
    ) ENGINE=InnoDB;

    alter table ss_user_role 
        add index FK1306854B6B2AC727 (user_id), 
        add constraint FK1306854B6B2AC727 
        foreign key (user_id) 
        references ss_user (id);

    alter table ss_user_role 
        add index FK1306854BC6000347 (role_id), 
        add constraint FK1306854BC6000347 
        foreign key (role_id) 
        references ss_role (id);
