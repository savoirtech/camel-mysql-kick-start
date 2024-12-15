CREATE DATABASE IF NOT EXISTS pizza_orders;

USE pizza_orders;

CREATE TABLE IF NOT EXISTS orders (
    id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(255) NOT NULL,
    pizza_type VARCHAR(255) NOT NULL,
    pizza_size VARCHAR(50) NOT NULL
    );