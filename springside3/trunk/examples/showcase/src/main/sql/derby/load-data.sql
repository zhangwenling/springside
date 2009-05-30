insert into USERS (LOGIN_NAME,PASSWORD,NAME,EMAIL,VERSION,CREATE_BY,CREATE_TIME) values('admin','admin','Admin','admin@springside.org.cn',1,'admin',CURRENT_TIMESTAMP);
insert into USERS (LOGIN_NAME,PASSWORD,NAME,EMAIL,VERSION,CREATE_BY,CREATE_TIME) values('user','user','Calvin','user@springside.org.cn',1,'admin',CURRENT_TIMESTAMP);
insert into USERS (LOGIN_NAME,PASSWORD,NAME,EMAIL,VERSION,CREATE_BY,CREATE_TIME) values('user2','user2','Jack','jack@springside.org.cn',1,'admin',CURRENT_TIMESTAMP);
insert into USERS (LOGIN_NAME,PASSWORD,NAME,EMAIL,VERSION,CREATE_BY,CREATE_TIME) values('user3','user3','Kate','kate@springside.org.cn',1,'admin',CURRENT_TIMESTAMP);
insert into USERS (LOGIN_NAME,PASSWORD,NAME,EMAIL,VERSION,CREATE_BY,CREATE_TIME) values('user4','user4','Sawyer','sawyer@springside.org.cn',1,'admin',CURRENT_TIMESTAMP);
insert into USERS (LOGIN_NAME,PASSWORD,NAME,EMAIL,VERSION,CREATE_BY,CREATE_TIME) values('user5','user5','Ben','ben@springside.org.cn',1,'admin',CURRENT_TIMESTAMP);

insert into ROLES (NAME) values('管理员');
insert into ROLES (NAME) values('用户');

insert into USERS_ROLES values(1,1);
insert into USERS_ROLES values(1,2);
insert into USERS_ROLES values(2,2);

insert into USERS_ROLES values(3,2);
insert into USERS_ROLES values(4,2);
insert into USERS_ROLES values(5,2);
insert into USERS_ROLES values(6,2);
