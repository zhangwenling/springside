insert into SS_USER (ID,LOGIN_NAME,PASSWORD,NAME,EMAIL,STATUS,VERSION,CREATE_BY,CREATE_TIME) values(1,'admin','admin','Admin','admin@springside.org.cn','enabled',1,'admin',CURRENT_TIMESTAMP);

insert into SS_ROLE (ID,NAME) values(1,'Admin');
insert into SS_ROLE (ID,NAME) values(2,'User');

insert into SS_USER_ROLE values(1,1);
insert into SS_USER_ROLE values(1,2);