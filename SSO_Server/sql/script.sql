CREATE DATABASE cas;
USE cas;

CREATE TABLE account(
    id          bigint unsigned PRIMARY KEY AUTO_INCREMENT,
    username    varchar(30) UNIQUE NOT NULL,
    password    varchar(256) NOT NULL,
    create_date datetime NOT NULL,
    modify_date datetime NOT NULL
);

