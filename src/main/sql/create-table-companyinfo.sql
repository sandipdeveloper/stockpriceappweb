/*
 * create companyinfo table for stock price app 
 * unique key -> symbol
 */ 
use stockprice;

create table if not exists companyinfo ( id bigint not null auto_increment
, name varchar(200)
, symbol varchar(20) not null
, unique(symbol)
, primary key (id));