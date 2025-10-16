/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  Coder
 * Created: 15/10/2025
 */
CREATE DATABASE IF NOT EXISTS libronova CHARACTER SET utf8mb4;
CREATE USER IF NOT EXISTS 'libronova'@'localhost' IDENTIFIED BY 'Qwe.12345';
GRANT ALL PRIVILEGES ON libronova.* TO 'libronova'@'localhost';
FLUSH PRIVILEGES;

USE libronova;

CREATE TABLE IF NOT EXISTS usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(100) NOT NULL,
    rol ENUM('admin', 'user') NOT NULL
);

CREATE TABLE IF NOT EXISTS book (
    id INT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    title VARCHAR(100) NOT NULL,
    author VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    total_copies INT DEFAULT 1,
    available_copies INT DEFAULT 1,
    reference_price DECIMAL(10,2) DEFAULT 0.00,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS member (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    deleted BOOLEAN NOT NULL DEFAULT FALSE,
    role ENUM('REGULAR','PREMIUM') NOT NULL DEFAULT 'REGULAR',
    access_level ENUM('READ_ONLY','READ_WRITE', 'MANAGE') NOT NULL DEFAULT 'READ_WRITE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS loan (
    id INT AUTO_INCREMENT PRIMARY KEY,
    member_id INT,
    book_id INT,
    date_loaned DATE,
    date_due DATE,
    returned BOOLEAN,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (book_id) REFERENCES book(id)
);



INSERT INTO usuarios (username, password, rol) VALUES
('admin', 'admin123', 'admin'),
('juan', 'juanpass', 'user'),
('maria', 'mariapass', 'user');

INSERT INTO book (isbn, title, author, category, total_copies, available_copies, reference_price)
VALUES
('9780140449266', 'La Odisea', 'Homero', 'Clásico', 5, 4, 45.00),
('9788420412140', 'Cien años de soledad', 'Gabriel García Márquez', 'Realismo mágico', 8, 7, 60.00),
('9788497592208', 'El alquimista', 'Paulo Coelho', 'Filosofía', 6, 5, 38.50),
('9788498383621', 'El nombre del viento', 'Patrick Rothfuss', 'Fantasía', 10, 9, 70.00);


INSERT INTO member (name, active, deleted, role, access_level) VALUES
('Juan Pérez', TRUE, FALSE, 'REGULAR', 'READ_WRITE'),
('María López', TRUE, FALSE, 'PREMIUM', 'MANAGE'),
('Carlos Ruiz', TRUE, FALSE, 'REGULAR', 'READ_ONLY');



INSERT INTO loan (member_id, book_id, date_loaned, date_due, returned) VALUES
(1, 1, '2025-10-01', '2025-10-15book', FALSE),
(2, 2, '2025-10-05', '2025-10-20', TRUE),
(3, 3, '2025-10-10', '2025-10-25', FALSE);
