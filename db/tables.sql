create table administrator
(
username char(15) primary key,
password char(100) not null,
password_key char(100) not null
);
create table designation
(
code int primary key,
title char(35) not null unique
);
create table employee
(
employee_id char(15) primary key,
name char(35) not null,
designation_code int not null,
gender char(1) not null,
is_indian bool not null,
date_of_birth date not null,
basic_salary decimal(10,2) not null,
pan_number char(25) not null unique,
aadhar_card_number char(25) not null unique
);
alter table employee add foreign key(designation_code) references designation(code);
