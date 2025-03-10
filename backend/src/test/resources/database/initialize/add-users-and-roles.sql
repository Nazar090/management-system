INSERT INTO roles (id, role)
VALUES (1, 'ADMIN'),
       (2, 'USER');

INSERT INTO users (email, password, nickname, name)
VALUES ('user1@example.com', 'password', 'Test', 'User'),
       ('user2@example.com', 'password2', 'Test2', 'User2'),
       ('john.doe@example.com', 'encodedPassword', 'John', 'Doe');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1),
       (2, 1);
