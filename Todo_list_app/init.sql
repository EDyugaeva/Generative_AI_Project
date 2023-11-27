CREATE DATABASE IF NOT EXISTS TodoListDb;
USE TodoListDb;

CREATE TABLE IF NOT EXISTS tasks
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255)
);

INSERT INTO tasks (name, description)
VALUES ('Task 1', 'Description 1'),
       ('Task 2', 'Description 2'),
       ('Task 3', 'Description 3'),
       ('Task 4', 'Description 4'),
       ('Task 5', 'Description 5'),
       ('Task 6', 'Description 6'),
       ('Task 7', 'Description 7'),
       ('Task 8', 'Description 8'),
       ('Task 9', 'Description 9'),
       ('Task 10', 'Description 10');

