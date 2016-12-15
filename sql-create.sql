create database vagas_db;
use vagas_db;

CREATE TABLE usuarios (user_id int auto_increment not null,
username varchar(50) not null,
surname varchar(50) not null,
user_hash varchar(100) not null,
first_name varchar(100) not null,
email varchar(100) not null,
primary key (user_id));

-- TODO: refactor
CREATE TABLE carros (placa varchar(10) not null,
cor varchar(50) not null,
modelo varchar(50) not null,
primary key (placa));

CREATE TABLE calcadas (calcada_id int auto_increment not null,
numero int unsigned,
cep varchar(8) not null,
rua varchar(100) not null,
latitude float not null,
longitude float not null,
is_busy boolean default false,
user_id int,
primary key (calcada_id),
foreign key (user_id) references usuarios (user_id));

CREATE TABLE horarios (horario_id int auto_increment not null,
EVENT_DAY ENUM('SUNDAY', 'MONDAY', 'TUESDAY', 'WEDNESDAY', 'THURSDAY', 'FRIDAY', 'SATURDAY'),
start_time time,
end_time time,
calcada_id int,
primary key (horario_id),
foreign key (calcada_id) references calcadas (calcada_id));
