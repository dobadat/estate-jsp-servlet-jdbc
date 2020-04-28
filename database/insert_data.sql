insert into role(code,name) values('MANAGER','Quản lý');
insert into role(code,name) values('STAFF','Nhân viên');

insert into user(username,password,fullname,status) values('nguyenvana','123456','Nguyễn Văn A',1);
insert into user(username,password,fullname,status) values('nguyenvanb','123456','Nguyễn Văn B',1);
insert into user(username,password,fullname,status) values('nguyenvanc','123456','Nguyễn Văn c',1);
insert into user(username,password,fullname,status) values('nguyenvand','123456','Nguyễn Văn d',1);

insert into user_role(userid,roleid) values(1,1);
insert into user_role(userid,roleid) values(2,2);
insert into user_role(userid,roleid) values(3,2);
insert into user_role(userid,roleid) values(4,2);