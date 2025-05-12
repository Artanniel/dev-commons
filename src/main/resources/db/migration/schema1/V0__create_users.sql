-- Set the PDB FREEPDB1 as the container
ALTER SESSION SET CONTAINER = FREEPDB1;

-- Creation of user schema1_user
BEGIN
EXECUTE IMMEDIATE 'CREATE USER schema1_user IDENTIFIED BY schema1_password';
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE != -1918 THEN -- Ignore error if user already exists
      RAISE;
END IF;
END;
/

-- Grant permissions to schema1_user
GRANT CONNECT, RESOURCE, UNLIMITED TABLESPACE TO schema1_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON SCHEMA_1.USER TO schema1_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON SCHEMA_1.CAR TO schema1_user;

-- Creation of user schema2_user
BEGIN
EXECUTE IMMEDIATE 'CREATE USER schema2_user IDENTIFIED BY schema2_password';
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE != -1918 THEN -- Ignore error if user already exists
      RAISE;
END IF;
END;
/

-- Grant permissions to schema2_user
GRANT CONNECT, RESOURCE, UNLIMITED TABLESPACE TO schema2_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON SCHEMA_2.MARKETPLACE TO schema2_user;
GRANT SELECT, INSERT, UPDATE, DELETE ON SCHEMA_2.PRODUCTS TO schema2_user;