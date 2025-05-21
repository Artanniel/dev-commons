ALTER SESSION SET CONTAINER = FREEPDB1;

DECLARE
    v_count NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_count
    FROM all_tables
    WHERE table_name = 'MARKETPLACE' AND owner = 'SCHEMA_2';
    IF v_count = 0 THEN
        EXECUTE IMMEDIATE '
        CREATE TABLE SCHEMA_2.MARKETPLACE (
                                              id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                              name VARCHAR2(100) NOT NULL,
                                              description VARCHAR2(500),
                                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        )
        ';
    END IF;
END;
/

DECLARE
    v_count NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_count
    FROM all_tables
    WHERE table_name = 'PRODUCTS' AND owner = 'SCHEMA_2';
    IF v_count = 0 THEN
        EXECUTE IMMEDIATE '
        CREATE TABLE SCHEMA_2.PRODUCTS (
                                           id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                                           name VARCHAR2(100) NOT NULL,
                                           price NUMBER(10,2) NOT NULL,
                                           marketplace_id NUMBER,
                                           CONSTRAINT fk_marketplace FOREIGN KEY (marketplace_id) REFERENCES SCHEMA_2.MARKETPLACE(id)
        )
        ';
    END IF;
END;
/