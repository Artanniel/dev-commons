-- Set the PDB FREEPDB1 as the container
ALTER SESSION SET CONTAINER = FREEPDB1;

-- Inserts into the SCHEMA_1.USER table
INSERT INTO SCHEMA_1.USER (name, email) VALUES ('João Silva', 'joao.silva@example.com');
INSERT INTO SCHEMA_1.USER (name, email) VALUES ('Maria Oliveira', 'maria.oliveira@example.com');
INSERT INTO SCHEMA_1.USER (name, email) VALUES ('Carlos Souza', 'carlos.souza@example.com');

-- Inserts for table SCHEMA_1.CAR
INSERT INTO SCHEMA_1.CAR (model, year, user_id) VALUES ('Toyota Corolla', 2020, 1);
INSERT INTO SCHEMA_1.CAR (model, year, user_id) VALUES ('Honda Civic', 2019, 1);
INSERT INTO SCHEMA_1.CAR (model, year, user_id) VALUES ('Ford Mustang', 2021, 2);