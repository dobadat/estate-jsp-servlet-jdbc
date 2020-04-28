select * from customer where 1=1  and customername='badat' limit 0,2;
select * from user_responsibility u inner join customer c on u.customerid = c.id where 1=1 and u.customerid =2;
select * from customer A  inner join user_responsibility U on U.customerid = A.id and U.customerid = 2 limit 0,10;
Select * from customer A  inner join user_responsibility U on U.customerid = A.id AND A.customerName like '%badat%' AND U.customerId = 1