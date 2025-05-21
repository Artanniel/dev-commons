ALTER SESSION SET CONTAINER = FREEPDB1;

INSERT INTO SCHEMA_1."USER" (name, email) VALUES ('João Silva', 'joao.silva@example.com');
INSERT INTO SCHEMA_1."USER" (name, email) VALUES ('Maria Oliveira', 'maria.oliveira@example.com');
INSERT INTO SCHEMA_1."USER" (name, email) VALUES ('Carlos Souza', 'carlos.souza@example.com');

INSERT INTO SCHEMA_1.CAR (model, year, user_id) VALUES ('Toyota Corolla', 2020, 1);
INSERT INTO SCHEMA_1.CAR (model, year, user_id) VALUES ('Honda Civic', 2019, 1);
INSERT INTO SCHEMA_1.CAR (model, year, user_id) VALUES ('Ford Mustang', 2021, 2);