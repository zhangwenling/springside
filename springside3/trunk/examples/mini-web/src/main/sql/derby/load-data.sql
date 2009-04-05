insert into USERS (LOGIN_NAME,PASSWORD,NAME,EMAIL) values('admin','admin','Admin','admin@springside.org.cn');
insert into USERS (LOGIN_NAME,PASSWORD,NAME,EMAIL) values('user','user','User','user@springside.org.cn');

insert into USERS (LOGIN_NAME,PASSWORD,NAME,EMAIL) values('user2','user2','Jack','jack@springside.org.cn');
insert into USERS (LOGIN_NAME,PASSWORD,NAME,EMAIL) values('user3','user3','Kate','kate@springside.org.cn');
insert into USERS (LOGIN_NAME,PASSWORD,NAME,EMAIL) values('user4','user4','Sawyer','sawyer@springside.org.cn');
insert into USERS (LOGIN_NAME,PASSWORD,NAME,EMAIL) values('user5','user5','Ben','ben@springside.org.cn');

insert into ROLES (NAME) values('管理员');
insert into ROLES (NAME) values('用户');

insert into AUTHORITIES (NAME,DISPLAY_NAME) values('A_VIEW_USER','查看用户');
insert into AUTHORITIES (NAME,DISPLAY_NAME) values('A_MODIFY_USER','管理用户');
insert into AUTHORITIES (NAME,DISPLAY_NAME) values('A_VIEW_ROLE','查看角色');
insert into AUTHORITIES (NAME,DISPLAY_NAME) values('A_MODIFY_ROLE','管理角色');

insert into RESOURCES (TYPE,VALUE) values('url','/security/user!save*');
insert into RESOURCES (TYPE,VALUE) values('url','/security/user!delete*');
insert into RESOURCES (TYPE,VALUE) values('url','/security/user*');
insert into RESOURCES (TYPE,VALUE) values('url','/security/role!save*');
insert into RESOURCES (TYPE,VALUE) values('url','/security/role!delete*');
insert into RESOURCES (TYPE,VALUE) values('url','/security/role*');

insert into USERS_ROLES values(1,1);
insert into USERS_ROLES values(1,2);
insert into USERS_ROLES values(2,2);

insert into USERS_ROLES values(3,2);
insert into USERS_ROLES values(4,2);
insert into USERS_ROLES values(5,2);
insert into USERS_ROLES values(6,2);

insert into ROLES_AUTHORITIES values(1,1);
insert into ROLES_AUTHORITIES values(1,2);
insert into ROLES_AUTHORITIES values(1,3);
insert into ROLES_AUTHORITIES values(1,4);
insert into ROLES_AUTHORITIES values(2,1);
insert into ROLES_AUTHORITIES values(2,3);

insert into RESOURCES_AUTHORITIES values(2,1);
insert into RESOURCES_AUTHORITIES values(2,2);
insert into RESOURCES_AUTHORITIES values(1,3);
insert into RESOURCES_AUTHORITIES values(4,4);
insert into RESOURCES_AUTHORITIES values(4,5);
insert into RESOURCES_AUTHORITIES values(3,6);
