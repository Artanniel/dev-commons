-- Set the PDB FREEPDB1 as the container
ALTER SESSION SET CONTAINER = FREEPDB1;

-- Inserts into the SCHEMA_2.MARKETPLACE table
INSERT INTO SCHEMA_2.MARKETPLACE (name, description) VALUES ('Mercado Livre', 'Plataforma de e-commerce líder na América Latina');
INSERT INTO SCHEMA_2.MARKETPLACE (name, description) VALUES ('Amazon Brasil', 'Loja global com entrega rápida');
INSERT INTO SCHEMA_2.MARKETPLACE (name, description) VALUES ('Shopee', 'Marketplace asiático com preços competitivos');

-- Inserts for the SCHEMA_2.PRODUCTS table
INSERT INTO SCHEMA_2.PRODUCTS (name, price, marketplace_id) VALUES ('Smartphone Samsung Galaxy', 1200.00, 1);
INSERT INTO SCHEMA_2.PRODUCTS (name, price, marketplace_id) VALUES ('Notebook Dell XPS', 4500.00, 2);
INSERT INTO SCHEMA_2.PRODUCTS (name, price, marketplace_id) VALUES ('Fone de Ouvido Bluetooth', 150.00, 3);