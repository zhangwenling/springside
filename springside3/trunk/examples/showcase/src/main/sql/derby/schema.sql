drop table USERS_ROLES;
drop table USERS;
drop table ROLES;


create table USERS (
ID integer primary key GENERATED ALWAYS AS IDENTITY,
LOGIN_NAME varchar(20) not null unique,
PASSWORD varchar(20),
NAME varchar(20),
EMAIL varchar(30)
);

create table ROLES (
ID integer primary key GENERATED ALWAYS AS IDENTITY,
NAME varchar(20) not null unique
);

create table USERS_ROLES (
USER_ID integer not null,
ROLE_ID integer not null,
FOREIGN KEY (ROLE_ID) references ROLES(ID),
FOREIGN KEY (USER_ID) references USERS(ID)
);