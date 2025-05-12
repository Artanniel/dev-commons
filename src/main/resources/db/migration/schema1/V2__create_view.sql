-- Set the PDB FREEPDB1 as the container
ALTER SESSION SET CONTAINER = FREEPDB1;

-- Creating a view that combines USER and CAR data in SCHEMA_1
CREATE OR REPLACE VIEW SCHEMA_1.USER_CAR_VIEW AS
SELECT u.id AS user_id, u.name AS user_name, u.email, c.id AS car_id, c.model, c.yaer
FROM SCHEMA_1.USER u
LEFT JOIN SCHEMA_1.CAR c ON c.user_id = u.id;