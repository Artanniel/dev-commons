CREATE OR REPLACE VIEW SCHEMA_1.USER_CAR_VIEW AS
SELECT
    u.id AS user_id,
    u.name AS user_name,
    u.email,
    c.id AS car_id,
    c.entity AS model,
    c.make_year
FROM SCHEMA_1.USER_APP u
LEFT JOIN SCHEMA_1.CAR c ON c.user_id = u.id;