ALTER SESSION SET CONTAINER = FREEPDB1;

DECLARE
    v_count_users NUMBER;
    v_count_roles NUMBER;
BEGIN
    SELECT COUNT(*) INTO v_count_users
    FROM dba_users
    WHERE username = 'USER_SCHEMA1';

    IF v_count_users > 0 THEN
        --EXECUTE IMMEDIATE 'DROP USER_APP user_schema1 CASCADE';
        DBMS_OUTPUT.PUT_LINE('User already exists!.');
    END IF;

    IF v_count_users < 1 THEN
        EXECUTE IMMEDIATE '
        CREATE USER user_schema1
            IDENTIFIED BY sc031210
            DEFAULT TABLESPACE users
            TEMPORARY TABLESPACE temp
            QUOTA UNLIMITED ON users
        ';
        DBMS_OUTPUT.PUT_LINE('User created.');
    END IF;

    SELECT COUNT(*) INTO v_count_roles
    FROM dba_roles
    WHERE role = 'USER_SCHEMA1';

    IF v_count_roles > 0 THEN
        --EXECUTE IMMEDIATE 'DROP ROLE user_schema1';
        DBMS_OUTPUT.PUT_LINE('Role USER_SCHEMA1 exists');
    END IF;

    IF v_count_roles < 1 THEN
        EXECUTE IMMEDIATE 'GRANT CONNECT TO user_schema1';
        EXECUTE IMMEDIATE 'GRANT SELECT, INSERT, UPDATE, DELETE ON SCHEMA_1.USER_APP TO user_schema1';
        EXECUTE IMMEDIATE 'GRANT SELECT, INSERT, UPDATE, DELETE ON SCHEMA_1.CAR TO user_schema1';
        EXECUTE IMMEDIATE 'GRANT ALTER ON SCHEMA_1.USER_APP TO user_schema1';
        EXECUTE IMMEDIATE 'GRANT ALTER ON SCHEMA_1.CAR TO user_schema1';
        --EXECUTE IMMEDIATE 'GRANT SELECT ON SCHEMA_1.USER_SEQ TO user_schema1';
        --EXECUTE IMMEDIATE 'GRANT SELECT ON SCHEMA_1.CAR_SEQ TO user_schema1';
        --EXECUTE IMMEDIATE 'GRANT DBA TO user_schema1';
        EXECUTE IMMEDIATE 'GRANT ALL PRIVILEGES ON SCHEMA_1.USER_APP TO user_schema1';
        EXECUTE IMMEDIATE 'GRANT ALL PRIVILEGES ON SCHEMA_1.CAR TO user_schema1';
        DBMS_OUTPUT.PUT_LINE('Permissions granted.');
    END IF;
END;
/