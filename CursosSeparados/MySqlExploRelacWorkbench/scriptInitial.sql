-- INTRODUÇÃO COMMAND LINE MYSQL
-- Conectar no MySQL
mysql -u root -p<senha se tiver>

-- exibir todos os databases:

show databases;

-- acessar um database: (bd criando na primeira parte dio_mysql)

use dio_mysql; 

-- exibir todas as tabelas:

show tables;

-- criando tabela da Parte2
CREATE TABLE cursos (
    id_curso INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    nome VARCHAR(10) NOT NULL
);

SHOW tables;

-- inserir dados:

INSERT INTO cursos (nome) VALUES ('MySQL');
INSERT INTO cursos (nome) VALUES ('HTML');
INSERT INTO cursos (nome) VALUES ('CSS');
INSERT INTO cursos (nome) VALUES ('Economia');

-- checando os dados
SELECT * FROM cursos;

-- atualizando dados:
UPDATE cursos SET nome ='HTML 5' where id_curso= 2;

-- deletar um registro expecifico:
SELECT * FROM cursos WHERE nome='Economia';

DELETE FROM cursos WHERE nome='Economia';

-- alterando tabela:
ALTER TABLE cursos
ADD COLUMN carga_horaria INT(2);

UPDATE cursos SET carga_horaria = 20;

UPDATE cursos SET carga_horaria = 40 where id_curso= 2;

-- deletar tabelas:
DROP TABLE <NOME_TABELA>;

-- Deletar DATABASE(Schema):
DROP DATABASE <NOME_SCHEMA>;

-- TROCAR DE DATABASE(Schema):
USE <nome_database>;

-- MODELO RELACIONAL
-- APAGAR TODAS AS TABELAS EXISTENTE EM dio_mysql (DROP TABLE <NOME_TABELA>

-- CRIANDO NOVAS TABELAS:

-- tabela author

CREATE TABLE `dio_mysql`.`author` (
    `id_author` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `born` DATETIME NOT NULL,
    PRIMARY KEY (`id_author`)
);
  
-- inserir dados:

INSERT INTO author (name, born) VALUES ('Maria', '1975-12-11');
INSERT INTO author (name, born) VALUES ('João', '1989-09-15');
INSERT INTO author (name, born) VALUES ('Pedro', '1965-07-05');

-- tabela videos:

CREATE TABLE `dio_mysql`.`videos` (
  `id_video` INT NOT NULL AUTO_INCREMENT,
  `fk_id_author` INT NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `likes` INT NULL,
  `dislikes` INT NULL,
  PRIMARY KEY (`id_video`),
  INDEX `fk_id_author_idx` (`fk_id_author` ASC) VISIBLE,
  CONSTRAINT `fk_id_author`
    FOREIGN KEY (`fk_id_author`)
    REFERENCES `dio_mysql`.`author` (`id_author`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
);

-- inserir dados:

INSERT INTO videos (fk_id_author, title, likes, dislikes) VALUES (1, 'MySQL',  10, 2);
INSERT INTO videos (fk_id_author, title, likes, dislikes) VALUES (2, 'HTML',  30, 1);
INSERT INTO videos (fk_id_author, title, likes, dislikes) VALUES (1, 'CSS',  18, 3);
INSERT INTO videos (fk_id_author, title, likes, dislikes) VALUES (3, 'JavaScript',  15, 8);
INSERT INTO videos (fk_id_author, title, likes, dislikes) VALUES (1, 'Phython',  50, 0);

-- realizando JOIN com as tabelas author e videos
SELECT 
    vi.*, ath.*
FROM
    dio_mysql.videos vi
        JOIN
    dio_mysql.author ath ON vi.fk_id_author = at.id_author;

-- tabela playlist:

CREATE TABLE `dio_mysql`.`playlist` (
  `id_playlist` INT NOT NULL AUTO_INCREMENT,
  `videos` VARCHAR(45) NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_playlist`));

-- tabela SEO
CREATE TABLE `dio_mysql`.`seo` (
  `id_seo` INT NOT NULL AUTO_INCREMENT,
  `categorys` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_seo`));
  


