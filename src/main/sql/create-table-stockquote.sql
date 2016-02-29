/*
 * create stock table for stock price app 
 * foreign key company_id -> company.id
 * company_id added to index by default
 * 
 */ 
use stockprice;

create table if not exists stockquote ( id bigint not null auto_increment
, company_id bigint not null
, record_date date not null
, price double
, primary key (id)
, foreign key (company_id) references companyinfo(id) on delete cascade);