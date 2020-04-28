CREATE TABLE customer (
  id bigint NOT NULL PRIMARY KEY auto_increment,
  customername VARCHAR(150) NOT NULL,
  phonenumber long NOT NULL,
  email VARCHAR(150) NULL,
  createddate DATETIME NULL,
  modifieddate DATETIME NULL,
  createdby VARCHAR(255) NULL,
  modifiedby VARCHAR(255) NULL
);
 CREATE TABLE user_responsibility (
  id bigint NOT NULL PRIMARY KEY auto_increment,
  customerid bigint NOT NULL,
  userid bigint NOT NULL
);
ALTER TABLE user_responsibility ADD CONSTRAINT fk_responsibility_user FOREIGN KEY (userid) REFERENCES user(id);
ALTER TABLE user_responsibility ADD CONSTRAINT fk_responsibility_customer FOREIGN KEY (customerid) REFERENCES customer(id);