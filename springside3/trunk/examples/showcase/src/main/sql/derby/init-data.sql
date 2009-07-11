insert into USERS (ID,LOGIN_NAME,PASSWORD,NAME,EMAIL,DESCRIPTION,STATUS,VERSION,CREATE_BY,CREATE_TIME) values(1,'admin','admin','Admin','admin@springside.org.cn','a good guy','enabled',1,'admin',CURRENT_TIMESTAMP);

insert into ROLES (ID,NAME) values(1,'管理员');
insert into ROLES (ID,NAME) values(2,'用户');

insert into USERS_ROLES values(1,1);
insert into USERS_ROLES values(1,2);