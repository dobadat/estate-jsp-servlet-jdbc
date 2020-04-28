insert into building(nam,type,district) values ('abc', 'abc', 'abc');

update building set name = '', type = '', district = '' where id = 1;

delete from building where id = 1;

select * from building order by name asc limit 0, 2;

select * from building limit 2, 2;

select * from building where name like '%TOWER%' AND district like '%QUAN_2%'
and buildingarea = 650 AND numberofbasement = 2
and costrent >= 18 and costrent <= 28
and (type like '%TANG_TRET%' or type like '%NGUYEN_CAN%')
and EXISTS
(SELECT * FROM rentarea ra WHERE (ra.buildingid = b.id AND ra.value >= 300 AND ra.value <= 500));



select * from building where (costrent >= 18 and costrent <= 28);

select * from building where (type like '%TANG_TRET%' or type like '%NGUYEN_CAN%');

SELECT * FROM building b
WHERE EXISTS
(SELECT * FROM rentarea ra WHERE (ra.buildingid = b.id AND ra.value >= 300 AND ra.value <= 500));

SELECT * FROM building b
WHERE EXISTS
(SELECT * FROM rentarea ra WHERE (ra.buildingid = b.id AND ra.value BETWEEN 300 AND 500));

SELECT * FROM building b INNER JOIN assignmentstaff s on s.buildingid = b.id
where s.staffid = 2;

SELECT * FROM building b LEFT JOIN assignmentstaff s on s.buildingid = b.id
where s.staffid = 2;

SELECT * FROM building b RIGHT JOIN assignmentstaff s on s.buildingid = b.id
where s.staffid = 2;

SELECT * FROM building b INNER JOIN assignmentstaff s on s.buildingid = b.id where name like '%tower%' and s.staffid = 2;
SELECT * FROM building b INNER JOIN assignmentstaff s on s.buildingid = b.id where s.staffid = 2;