-- Create a new database called 'catalogo'
 CREATE DATABASE catalogo;
 USE catalogo;

-- Create a new table called '[tb_diretor]' in schema '[catalogo]'

-- Create the table in the specified schema
CREATE TABLE tb_diretor
(
    id_diretor INT NOT NULL, -- Primary Key column
    nome_diretor VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_diretor)
);

SELECT * FROM tb_diretor;

-- Create a new table called '[tb_filme]' in schema '[catalogo]'

-- Create the table in the specified schema
CREATE TABLE tb_filme
(
    id_filme INT NOT NULL, -- Primary Key column
    nome_filme VARCHAR(50) NOT NULL,
    id_diretor INT NOT NULL,
    id_produtora INT NOT NULL,
    genero VARCHAR(25),
    PRIMARY KEY (id_filme),
    FOREIGN KEY (id_diretor) REFERENCES tb_diretor(id_diretor)
);

SELECT * FROM tb_filme;

-- Create a new table called '[tb_produtora]' in schema '[catalogo]'

-- Create the table in the specified schema
CREATE TABLE tb_produtora
(
    id_produtora INT NOT NULL, -- Primary Key column
    nome_produtora VARCHAR(50) NOT NULL,
    PRIMARY KEY (id_produtora)
);

SELECT * FROM tb_produtora;

--modify table tb_filme add FOREIGN KEY id_produtora
ALTER TABLE tb_filme
ADD CONSTRAINT fk_id_produtora FOREIGN KEY (id_produtora) REFERENCES tb_produtora(id_produtora);