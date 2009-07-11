insert into USERS (ID,LOGIN_NAME,PASSWORD,NAME,EMAIL) values(1,'admin','admin','Admin','admin@springside.org.cn');

insert into ROLES (ID,NAME) values(1,'管理员');
insert into ROLES (ID,NAME) values(2,'用户');

insert into USERS_ROLES values(1,1);
insert into USERS_ROLES values(1,2);