
-- CRIAR SCHEMA:

CREATE DATABASE `movies-controll` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- ACESSAR SCHEMA

use `movies-controll`;

-- CRIAR TABLE:

CREATE TABLE movies (
    id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    type INT NOT NULL,
    name VARCHAR(30) NOT NULL,
    total_ep INT NULL,
    atual_ep INT NULL,
    last_view DATETIME DEFAULT CURRENT_TIMESTAMP
);
-- INSER DADOS TESTES:

INSERT INTO `movies-controll`.`movies` (`type`, `name`, `total_ep`, `atual_ep`) VALUES ('0', 'Friends', '10', '1');
INSERT INTO `movies-controll`.`movies` (`type`, `name`) VALUES ('1', 'Lie To Me');