create database if not exists timetable;
use timetable;

create table if not exists job (
	jobId varchar(50) primary key,
    position varchar(50) not null
);

create table if not exists trackWarrant (
	id int auto_increment primary key,
    jobId varchar(50) references job(jobId),
    origin varchar(50) not null,
    destination varchar(50) not null
);

 create table if not exists trackWarrantNodes (
	id int auto_increment primary key,
	trackWarrant int references trackWarrant(id),
    node varchar(50) not null
 );
