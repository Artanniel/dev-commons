ALTER SESSION SET CONTAINER = FREEPDB1;

CREATE OR REPLACE VIEW SCHEMA_2.MARKETPLACE_PRODUCTS_VIEW AS
SELECT m.id AS marketplace_id, m.name as marketplace_name, m.description, p.id AS product_id, p.name as product_name, p.price
FROM SCHEMA_2.MARKETPLACE m
LEFT JOIN SCHEMA_2.PRODUCTS p ON p.marketplace_id = m.id;