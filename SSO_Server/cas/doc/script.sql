CREATE DATABASE cas;
USE cas;

CREATE TABLE Account(
    id          bigint unsigned PRIMARY KEY AUTO_INCREMENT,
    username    varchar(30) UNIQUE NOT NULL,
    password    varchar(30) NOT NULL,
    create_date datetime NOT NULL,
    modify_date datetime NOT NULL
);

