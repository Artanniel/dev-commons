-- Creating schema SCHEMA_1 (if necessary)
BEGIN
EXECUTE IMMEDIATE 'CREATE SCHEMA AUTHORIZATION SCHEMA_1';
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE != -1918 THEN -- Ignore error if the schema already exists
      RAISE;
END IF;
END;
/

-- Creating schema SCHEMA_2 (if necessary)
BEGIN
EXECUTE IMMEDIATE 'CREATE SCHEMA AUTHORIZATION SCHEMA_2';
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE != -1918 THEN -- Ignore error if the schema already exists
      RAISE;
END IF;
END;
/

-- Creation of the USER table in the SCHEMA_1 schema
DECLARE
v_count NUMBER;
BEGIN
  -- Checks if the USER table exists in SCHEMA_1
SELECT COUNT(*) INTO v_count
FROM all_tables
WHERE table_name = 'USER' AND owner = 'SCHEMA_1';

-- Create the table only if it does not exist
IF v_count = 0 THEN
    EXECUTE IMMEDIATE '
      CREATE TABLE SCHEMA_1.USER (
        id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        name VARCHAR2(100) NOT NULL,
        email VARCHAR2(100) NOT NULL
      )
    ';
END IF;
END;
/

-- Creation of the CAR table in the SCHEMA_1 schema
DECLARE
v_count NUMBER;
BEGIN
  -- Verifica se a tabela CARRO existe no SCHEMA_1
SELECT COUNT(*) INTO v_count
FROM all_tables
WHERE table_name = 'CAR' AND owner = 'SCHEMA_1';

-- Cria a tabela apenas se ela não existir
IF v_count = 0 THEN
    EXECUTE IMMEDIATE '
      CREATE TABLE SCHEMA_1.CAR (
        id NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
        model VARCHAR2(100) NOT NULL,
        year NUMBER NOT NULL
      )
    ';
END IF;
END;
/