insert into USERS (ID,LOGIN_NAME,PASSWORD,NAME,EMAIL,STATUS,VERSION,CREATE_BY,CREATE_TIME) values(1,'admin','admin','Admin','admin@springside.org.cn','enabled',1,'admin',CURRENT_TIMESTAMP);

insert into ROLES (ID,NAME) values(1,'Admin');
insert into ROLES (ID,NAME) values(2,'User');

insert into USERS_ROLES values(1,1);
insert into USERS_ROLES values(1,2);