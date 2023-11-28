CREATE DATABASE IF NOT EXISTS bookstore;
USE bookstore;

-- Create table for genres
CREATE TABLE genres (
                       id INT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL
);

-- Create table for authors
CREATE TABLE authors (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        first_name VARCHAR(255) NOT NULL,
                        last_name VARCHAR(255) NOT NULL,
                        year_of_birth INT,
                        country_of_residence VARCHAR(255)
);

-- Create table for books
CREATE TABLE books (
                      id INT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(255) NOT NULL,
                      author_id INT,
                      genre_id INT,
                      price DECIMAL(10, 2),
                      quantity_available INT,
                      FOREIGN KEY (author_id) REFERENCES authors(id),
                      FOREIGN KEY (genre_id) REFERENCES genres(id)
);

-- Insert values into genres table
INSERT INTO genres (name) VALUES
                              ('detective'),
                              ('history'),
                              ('fantasy'),
                              ('drama'),
                              ('biography'),
                              ('mystery');

-- Insert values into authors table
INSERT INTO authors (first_name, last_name, year_of_birth, country_of_residence) VALUES
                                                                                     ('John', 'Doe', 1980, 'USA'),
                                                                                     ('Jane', 'Smith', 1975, 'UK'),
                                                                                     ('Michael', 'Johnson', 1990, 'Canada'),
                                                                                     ('Emily', 'Brown', 1985, 'Australia');

-- Insert values into books table
INSERT INTO books (title, author_id, genre_id, price, quantity_available) VALUES
                                                                              ('The Detective Chronicles', 1, 1, 29.99, 100),
                                                                              ('History Through the Ages', 2, 2, 39.99, 75),
                                                                              ('Fantasy World Adventures', 3, 3, 24.99, 120),
                                                                              ('Drama in the City', 4, 4, 34.99, 50),
                                                                              ('Biography of a Pioneer', 1, 5, 49.99, 80),
                                                                              ('Mystery Unveiled', 2, 6, 19.99, 110);