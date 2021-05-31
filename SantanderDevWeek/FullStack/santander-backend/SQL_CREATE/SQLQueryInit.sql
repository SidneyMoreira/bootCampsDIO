CREATE DATABASE db_santander;

USE db_santander;

-- Create the table in the specified schema
CREATE TABLE tb_stock
(
    id NUMERIC(9) NOT NULL, 
    "name" VARCHAR(100) NOT NULL,
    price NUMERIC(8,2) NOT NULL,
    variation NUMERIC(5,2) NOT NULL,
    "date" date NOT NULL,
    CONSTRAINT tb_stock_pkey PRIMARY KEY (id)
);

--create seguence
CREATE SEQUENCE hibernate_sequence START 1;