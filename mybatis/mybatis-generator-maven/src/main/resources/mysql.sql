
drop table if exists city;
drop table if exists hotel;

create table city (city_id int primary key auto_increment, name varchar(100), state varchar(100), country varchar(100));
create table hotel (hotel_id int primary key auto_increment, city_id int, name varchar(100), address varchar(100), zip varchar(100));

insert into city (name, state, country) values ('San Francisco', 'CA', 'US');
insert into hotel(city_id, name, address, zip) values (1, 'Conrad Treasury Place', 'William & George Streets', '4001');
