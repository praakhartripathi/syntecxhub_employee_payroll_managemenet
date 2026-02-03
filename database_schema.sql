-- Payroll Management System Database Schema
-- This script creates the necessary tables for the payroll management system

-- Create database
CREATE DATABASE IF NOT EXISTS payroll_db;
USE payroll_db;

-- Create employees table
CREATE TABLE IF NOT EXISTS employees (
    emp_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    department VARCHAR(50) NOT NULL,
    designation VARCHAR(50) NOT NULL,
    join_date DATE NOT NULL,
    base_salary DECIMAL(12, 2) NOT NULL,
    hra DECIMAL(12, 2) NOT NULL DEFAULT 0,
    allowance DECIMAL(12, 2) NOT NULL DEFAULT 0,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_email (email),
    INDEX idx_active (active),
    INDEX idx_department (department)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create payslips table
CREATE TABLE IF NOT EXISTS payslips (
    payslip_id INT AUTO_INCREMENT PRIMARY KEY,
    emp_id INT NOT NULL,
    month INT NOT NULL,
    year INT NOT NULL,
    base_salary DECIMAL(12, 2) NOT NULL,
    hra DECIMAL(12, 2) NOT NULL,
    allowance DECIMAL(12, 2) NOT NULL,
    gross_salary DECIMAL(12, 2) NOT NULL,
    income_tax DECIMAL(12, 2) NOT NULL,
    provident_fund DECIMAL(12, 2) NOT NULL,
    health_insurance DECIMAL(12, 2) NOT NULL,
    total_deductions DECIMAL(12, 2) NOT NULL,
    net_salary DECIMAL(12, 2) NOT NULL,
    generated_date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_emp_id FOREIGN KEY (emp_id) REFERENCES employees(emp_id) ON DELETE CASCADE,
    UNIQUE KEY unique_payslip (emp_id, month, year),
    INDEX idx_emp_id (emp_id),
    INDEX idx_month_year (month, year),
    INDEX idx_generated_date (generated_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create audit log table for tracking changes
CREATE TABLE IF NOT EXISTS salary_audit_log (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    emp_id INT NOT NULL,
    old_base_salary DECIMAL(12, 2),
    new_base_salary DECIMAL(12, 2),
    old_hra DECIMAL(12, 2),
    new_hra DECIMAL(12, 2),
    old_allowance DECIMAL(12, 2),
    new_allowance DECIMAL(12, 2),
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    changed_by VARCHAR(50),
    CONSTRAINT fk_audit_emp_id FOREIGN KEY (emp_id) REFERENCES employees(emp_id) ON DELETE CASCADE,
    INDEX idx_emp_id (emp_id),
    INDEX idx_changed_at (changed_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Create sample data (optional)
-- Uncomment to insert sample employees

INSERT INTO employees (name, email, department, designation, join_date, base_salary, hra, allowance) VALUES
('Raj Kumar', 'raj.kumar@company.com', 'Engineering', 'Senior Developer', '2020-01-15', 75000, 15000, 5000),
('Priya Singh', 'priya.singh@company.com', 'HR', 'HR Manager', '2019-06-01', 60000, 12000, 3000),
('Amit Patel', 'amit.patel@company.com', 'Finance', 'Finance Officer', '2021-03-20', 50000, 10000, 2000),
('Neha Sharma', 'neha.sharma@company.com', 'Operations', 'Operations Coordinator', '2022-01-10', 40000, 8000, 1500);

-- Display tables
SHOW TABLES;
