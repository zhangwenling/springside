drop table USERS_ROLES;
drop table USERS;
drop table ROLES;

drop table LOGS;

create table USERS (
ID integer primary key GENERATED ALWAYS as IDENTITY,
LOGIN_NAME varchar(20) not null unique,
PASSWORD varchar(20),
NAME varchar(20),
EMAIL varchar(30),
DESCRIPTION clob,
STATUS varchar(30),
VERSION integer,
CREATE_TIME timestamp,
CREATE_BY varchar(20),
LAST_MODIFY_TIME timestamp,
LAST_MODIFY_BY varchar(20)
);
create unique index USERS_LOGIN_NAME_INDEX on USERS(LOGIN_NAME);

create table ROLES (
ID integer primary key GENERATED ALWAYS as IDENTITY,
NAME varchar(20) not null unique
);

create table USERS_ROLES (
USER_ID integer not null,
ROLE_ID integer not null,
FOREIGN KEY (ROLE_ID) references ROLES(ID),
FOREIGN KEY (USER_ID) references USERS(ID)
);

create table LOGS (
THREAD_NAME varchar(255),
LOGGER_NAME varchar(255),
TIMESTAMP timestamp,
LEVEL varchar(20),
MESSAGE varchar(255)
);