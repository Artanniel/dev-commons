CREATE TABLE IF NOT EXISTS SCHEMA_2.MARKETPLACE (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    description VARCHAR2(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );

CREATE TABLE IF NOT EXISTS SCHEMA_2.PRODUCTS (
    id NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    price NUMBER(10,2) NOT NULL,
    marketplace_id NUMBER,
    CONSTRAINT fk_marketplace FOREIGN KEY (marketplace_id) REFERENCES SCHEMA_2.MARKETPLACE(id)
    );