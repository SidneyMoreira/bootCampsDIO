-- CRIAR SCHEMA:

CREATE DATABASE `game-shop` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- ACESSAR SCHEMA

use `game-shop`;

-- CRIAR TABLE categoria:

CREATE TABLE `game-shop`.`categorias` (
  `id_categoria` INT NOT NULL AUTO_INCREMENT,
  `nome_categoria` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id_categoria`));
 
-- CRIAR TABLE game:

CREATE TABLE `game-shop`.`games` (
  `id_game` INT NOT NULL AUTO_INCREMENT,
  `nome_game` VARCHAR(30) NOT NULL,
  `preco` FLOAT NOT NULL,
  `fk_idcategoria` INT NOT NULL,
  PRIMARY KEY (`id_game`),
  INDEX `fk_idcategoria_idx` (`fk_idcategora` ASC) VISIBLE,
  CONSTRAINT `fk_idcategoria`
    FOREIGN KEY (`fk_idcategora`)
    REFERENCES `game-shop`.`categorias` (`id_categoria`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
    
-- CRIAR TABELA clientes:
CREATE TABLE `game-shop`.`clientes` (
  `id_cliente` INT NOT NULL AUTO_INCREMENT,
  `nome_cliente` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`id_cliente`));
  
-- CRIAR TABELA PEDIDOS:
CREATE TABLE `game-shop`.`pedidos` (
  `id_pedido` INT NOT NULL AUTO_INCREMENT,
  `fk_idcliente` INT NOT NULL,
  `fk_idgames` INT NOT NULL,
  `qtd` INT NOT NULL,
  PRIMARY KEY (`id_pedido`),
  INDEX `fk_idclientes_idx` (`fk_idcliente` ASC) VISIBLE,
  INDEX `fk_idgames_idx` (`fk_idgames` ASC) VISIBLE,
  CONSTRAINT `fk_idclientes`
    FOREIGN KEY (`fk_idcliente`)
    REFERENCES `lojagame`.`clientes` (`id_cliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_idgames`
    FOREIGN KEY (`fk_idgames`)
    REFERENCES `lojagame`.`games` (`id_game`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
  
-- INSER DADOS TESTES:

INSERT INTO `game-shop`.`categorias` (`nome_categoria`) VALUES ('Acão');
INSERT INTO `game-shop`.`categorias` (`nome_categoria`) VALUES ('Aventura');
INSERT INTO `game-shop`.`categorias` (`nome_categoria`) VALUES ('Esportes');

INSERT INTO `game-shop`.`games` (`nome_game`, `preco`, `fk_idcategoria`) VALUES ('COD', '300', '1');
INSERT INTO `game-shop`.`games` (`nome_game`, `preco`, `fk_idcategoria`) VALUES ('Among US', '200', '2');
INSERT INTO `game-shop`.`games` (`nome_game`, `preco`, `fk_idcategoria`) VALUES ('NBA 2K', '200', '3');

INSERT INTO `game-shop`.`clientes` (`nome_cliente`) VALUES ('João');
INSERT INTO `game-shop`.`clientes` (`nome_cliente`) VALUES ('José');

INSERT INTO `game-shop`.`pedidos` (`fk_idcliente`, `fk_idgames`, `qtd`) VALUES ('1', '2', '2');
INSERT INTO `game-shop`.`pedidos` (`fk_idcliente`, `fk_idgames`, `qtd`) VALUES ('2', '1', '3');
INSERT INTO `game-shop`.`pedidos` (`fk_idcliente`, `fk_idgames`, `qtd`) VALUES ('2', '3', '1');
INSERT INTO `game-shop`.`pedidos` (`fk_idcliente`, `fk_idgames`, `qtd`) VALUES ('1', '1', '1');


-- selects testes:
SELECT 
    ga.nome_game, ga.preco, ca.nome_categoria
FROM
    `game-shop`.`games` ga
        JOIN
    `game-shop`.`categorias` ca ON ga.fk_idcategoria = ca.id_categoria;
    
    
SELECT 
    clie.nome_cliente,
    pe.qtd,
    ga.nome_game,
    ga.preco,
    ca.nome_categoria
FROM
    `game-shop`.pedidos pe
        JOIN
    `game-shop`.clientes clie ON pe.fk_idcliente = clie.id_cliente
        JOIN
    `game-shop`.games ga ON pe.fk_idgames = ga.id_game
        JOIN
    `game-shop`.categorias ca ON ga.fk_idcategoria = ca.id_categoria;