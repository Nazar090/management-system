-- Clear the data
DELETE FROM users_roles;

ALTER SEQUENCE roles_id_seq RESTART WITH 1;

DELETE FROM roles;
ALTER SEQUENCE roles_id_seq RESTART WITH 1;

DELETE FROM users;
ALTER SEQUENCE users_id_seq RESTART WITH 1;

