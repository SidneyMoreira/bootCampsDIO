-- criando o BD
CREATE DATABASE dio_mysql;

-- acessando o BD:
use dio_mysql;

-- cria a primeira tabela:
-- SEM PRIMARY KEY (não recomendado)
CREATE TABLE pessoas (
    nome VARCHAR(20) NOT NULL,
    nascimento DATE
);

-- COM PRIMARY KEY
CREATE TABLE pessoa (
	id INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nome VARCHAR(20) NOT NULL,
    nascimento DATE
);

-- INSERINDO DADOS NAS TABELAS

INSERT INTO pessoas (nome, nascimento) VALUES ('João', '1975-12-11');

INSERT INTO pessoa (nome, nascimento) VALUES ('João', '1975-12-11');
INSERT INTO pessoa (nome, nascimento) VALUES ('Maria', '1975-12-11');
INSERT INTO pessoa (nome, nascimento) VALUES ('José', '1989-09-15');
INSERT INTO pessoa (nome, nascimento) VALUES ('Izabel', '1990-02-18');
INSERT INTO pessoa (nome, nascimento) VALUES ('Pedro', '1965-07-05');

-- SELECIONANDO DADOS:
SELECT * FROM pessoas;

SELECT * FROM pessoa WHERE id = 1;

SELECT * FROM pessoa  ORDER BY nome;

SELECT * FROM pessoa  ORDER BY nome DESC;

-- ATUALIZANDO DADOS:

-- Não recomendado:
UPDATE pessoa SET nome = 'João Moreira' ;

-- Recomendado:
UPDATE pessoa SET nome = 'João Moreira' WHERE id = 1;

-- DELETANDO DADOS:

-- nao recomendado:
DELETE FROM pessoa;

-- recomendado
DELETE FROM pessoa where id= 4;

INSERT INTO pessoa (nome, nascimento) VALUES ('Izabel', '1990-02-18');

-- alterando tabela:
ALTER TABLE `dio_mysql`.`pessoa` 
ADD COLUMN `genero` VARCHAR(1) NOT NULL AFTER `nascimento`;

-- atualizando a nova coluna:
UPDATE `dio_mysql`.`pessoa` SET `genero` = 'M' WHERE (`id` = '5');
UPDATE `dio_mysql`.`pessoa` SET `genero` = 'F' WHERE (`id` = '6');
UPDATE `dio_mysql`.`pessoa` SET `genero` = 'M' WHERE (`id` = '3');
UPDATE `dio_mysql`.`pessoa` SET `genero` = 'F' WHERE (`id` = '2');
UPDATE `dio_mysql`.`pessoa` SET `genero` = 'M' WHERE (`id` = '1');

-- Agrupando registros e totalizando

SELECT COUNT(id), genero FROM pessoa GROUP BY genero;