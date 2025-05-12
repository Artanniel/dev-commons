-- Set the PDB FREEPDB1 as the container
ALTER SESSION SET CONTAINER = FREEPDB1;

-- Creating a view that combines USER and CAR data in SCHEMA_2
CREATE OR REPLACE VIEW SCHEMA_2.MARKETPLACE_PRODUCT_VIEW AS
SELECT m.id AS marketplace_id, m.name AS marketplace_name, m.description, p.id AS product_id, p.name, p.price
FROM SCHEMA_2.MARKETPLACE m
LEFT JOIN SCHEMA_2.PRODUCTS p ON p.marketplace_id = m.id;