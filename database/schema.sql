-- ============================================================
-- Employee Management System - Database Schema
-- Run this in MySQL Workbench before starting the application
-- ============================================================

CREATE DATABASE IF NOT EXISTS employee_management;
USE employee_management;

-- Departments table
CREATE TABLE departments (
    dept_id INT AUTO_INCREMENT PRIMARY KEY,
    dept_name VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Users table (for login)
CREATE TABLE users (
    user_id   INT AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(50) NOT NULL UNIQUE,
    password  VARCHAR(255) NOT NULL,          -- store hashed passwords
    role      ENUM('ADMIN','EMPLOYEE') NOT NULL DEFAULT 'EMPLOYEE',
    is_active TINYINT(1) DEFAULT 1,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Employees table
CREATE TABLE employees (
    emp_id      INT AUTO_INCREMENT PRIMARY KEY,
    user_id     INT,
    first_name  VARCHAR(50) NOT NULL,
    last_name   VARCHAR(50) NOT NULL,
    email       VARCHAR(100) NOT NULL UNIQUE,
    phone       VARCHAR(20),
    dept_id     INT,
    designation VARCHAR(100),
    salary      DECIMAL(12,2) DEFAULT 0.00,
    hire_date   DATE,
    address     TEXT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id)  REFERENCES users(user_id) ON DELETE SET NULL,
    FOREIGN KEY (dept_id)  REFERENCES departments(dept_id) ON DELETE SET NULL
);

-- ============================================================
-- Seed Data
-- ============================================================

INSERT INTO departments (dept_name) VALUES
('Engineering'), ('Human Resources'), ('Finance'),
('Marketing'), ('Operations'), ('Sales');

-- Passwords below are MD5 hashes:
--   admin123  → 0192023a7bbd73250516f069df18b500
--   emp123    → 0a6e683c6e0318d73cc1d9be9a71f92b
INSERT INTO users (username, password, role) VALUES
('admin',    MD5('admin123'), 'ADMIN'),
('john.doe', MD5('emp123'),   'EMPLOYEE'),
('jane.doe', MD5('emp123'),   'EMPLOYEE');

INSERT INTO employees (user_id, first_name, last_name, email, phone, dept_id, designation, salary, hire_date) VALUES
(2, 'John', 'Doe',  'john.doe@company.com',  '9876543210', 1, 'Software Engineer', 75000.00, '2022-06-15'),
(3, 'Jane', 'Doe',  'jane.doe@company.com',  '9876543211', 2, 'HR Manager',        65000.00, '2021-03-20');
