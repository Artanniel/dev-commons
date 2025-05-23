-- Creation of the MARKETPLACE table in the SCHEMA_2 schema
CREATE TABLE SCHEMA_2.MARKETPLACE (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Creating the PRODUCTS table in the SCHEMA_2 schema
CREATE TABLE SCHEMA_2.PRODUCTS (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    marketplace_id BIGINT,
    CONSTRAINT fk_marketplace FOREIGN KEY (marketplace_id) REFERENCES SCHEMA_2.MARKETPLACE(id)
);