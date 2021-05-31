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

CREATE TABLE `dio_mysql`.`authors` (
    `id_author` INT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(45) NOT NULL,
    `born` DATETIME NOT NULL,
    PRIMARY KEY (`id_author`)
);
  
-- inserir dados:

INSERT INTO `dio_mysql`.`authors` (name, born) VALUES ('Maria', '1975-12-11');
INSERT INTO `dio_mysql`.`authors` (name, born) VALUES ('João', '1989-09-15');
INSERT INTO `dio_mysql`.`authors` (name, born) VALUES ('Pedro', '1965-07-05');

-- tabela SEO
CREATE TABLE `dio_mysql`.`seo` (
  `id_seo` INT NOT NULL AUTO_INCREMENT,
  `category` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_seo`));
  
-- inserir dados:
INSERT INTO `dio_mysql`.`seo` (category) VALUES ('Frontend');
INSERT INTO `dio_mysql`.`seo` (category) VALUES ('Backend');


-- tabela videos:

CREATE TABLE `dio_mysql`.`videos` (
  `id_video` INT NOT NULL AUTO_INCREMENT,
  `fk_author` INT NOT NULL,
  `fk_seo` INT NOT NULL,
  `title` VARCHAR(45) NOT NULL,
  `likes` INT NULL,
  `dislikes` VARCHAR(45) NULL,
  PRIMARY KEY (`id_video`),
  INDEX `fk_author_idx` (`fk_author` ASC) VISIBLE,
  INDEX `fk_seo_idx` (`fk_seo` ASC) VISIBLE,
  CONSTRAINT `fk_author`
    FOREIGN KEY (`fk_author`)
    REFERENCES `dio_mysql`.`authors` (`id_author`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_seo`
    FOREIGN KEY (`fk_seo`)
    REFERENCES `dio_mysql`.`seo` (`id_seo`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);

-- inserir dados:

INSERT INTO `dio_mysql`.`videos` (fk_author, title, fk_seo, likes, dislikes) VALUES (1, 'MySQL', 2, 10, 2);
INSERT INTO `dio_mysql`.`videos` (fk_author, title, fk_seo, likes, dislikes) VALUES (2, 'HTML',  1, 30, 1);
INSERT INTO `dio_mysql`.`videos` (fk_author, title, fk_seo, likes, dislikes) VALUES (1, 'CSS', 1 , 18, 3);
INSERT INTO `dio_mysql`.`videos` (fk_author, title, fk_seo, likes, dislikes) VALUES (3, 'JavaScript',  1, 15, 8);
INSERT INTO `dio_mysql`.`videos` (fk_author, title, fk_seo, likes, dislikes) VALUES (1, 'Phython',  2, 50, 0);

-- realizando JOIN com as tabelas author e videos
SELECT 
    vi.id_video,
    vi.title,
    ath.name author,
    se.category,
    vi.likes,
    vi.dislikes
FROM
    dio_mysql.videos vi
        JOIN
    dio_mysql.authors ath ON vi.fk_author = ath.id_author
    JOIN
    dio_mysql.seo se ON vi.fk_seo = se.id_seo
ORDER BY id_video;



-- tabela playlist:

CREATE TABLE `dio_mysql`.`playlists` (
  `id_playlist` INT NOT NULL AUTO_INCREMENT,
  `fk_author` INT NOT NULL,
  `name_pl` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_playlist`),
  INDEX `fk_author_idx` (`fk_author` ASC) VISIBLE,
  CONSTRAINT `fk_author_pl`
    FOREIGN KEY (`fk_author`)
    REFERENCES `dio_mysql`.`authors` (`id_author`)
    ON DELETE CASCADE
    ON UPDATE CASCADE);
    

INSERT INTO `dio_mysql`.`playlists` (fk_author, name_pl) VALUES (1,'HTML + CSS');
INSERT INTO `dio_mysql`.`playlists` (fk_author, name_pl) VALUES (2,'HTML + MySQL + JavaScript');
INSERT INTO `dio_mysql`.`playlists` (fk_author, name_pl) VALUES (3,'Phython + JavaScript');

CREATE TABLE `dio_mysql`.`videos_playlist` (
  `id_vp` INT NOT NULL AUTO_INCREMENT,
  `fk_video` INT NOT NULL,
  `fk_playlist` INT NOT NULL,
  PRIMARY KEY (`id_vp`),
  INDEX `fk_videos_idx` (`fk_video` ASC) VISIBLE,
  INDEX `fk_playlist_idx` (`fk_playlist` ASC) VISIBLE,
  CONSTRAINT `fk_video`
    FOREIGN KEY (`fk_video`)
    REFERENCES `dio_mysql`.`videos` (`id_video`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_playlist`
    FOREIGN KEY (`fk_playlist`)
    REFERENCES `dio_mysql`.`playlists` (`id_playlist`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO `dio_mysql`.`videos_playlist` (fk_video, fk_playlist) VALUES (2,1);
INSERT INTO `dio_mysql`.`videos_playlist` (fk_video, fk_playlist) VALUES (3,1);
INSERT INTO `dio_mysql`.`videos_playlist` (fk_video, fk_playlist) VALUES (2,2);
INSERT INTO `dio_mysql`.`videos_playlist` (fk_video, fk_playlist) VALUES (1,2);
INSERT INTO `dio_mysql`.`videos_playlist` (fk_video, fk_playlist) VALUES (4,2);
INSERT INTO `dio_mysql`.`videos_playlist` (fk_video, fk_playlist) VALUES (5,3);
INSERT INTO `dio_mysql`.`videos_playlist` (fk_video, fk_playlist) VALUES (3,3);


SELECT 
	vp.id_vp,
--    vi.id_video,
    vi.title,
    ath2.name author,
--    pl.id_playlist,
    pl.name_pl,
    ath.name author_pl,
    se.category
FROM
    dio_mysql.videos_playlist vp
        JOIN
    dio_mysql.videos vi ON vp.fk_video = vi.id_video
    JOIN
    dio_mysql.playlists pl ON vp.fk_playlist = pl.id_playlist 
    JOIN
    dio_mysql.authors ath ON pl.fk_author = ath.id_author
    JOIN
	dio_mysql.authors ath2 ON vi.fk_author = ath2.id_author
    JOIN
    dio_mysql.seo se ON vi.fk_seo = se.id_seo
ORDER BY vp.id_vp;