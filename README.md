# Payroll Management System

A comprehensive Java-based payroll management system that automates employee salary calculations, payslip generation, and payroll data management using MySQL database.

## Features

### ✓ Employee Management
- Add new employees with salary details
- Update employee salary components (Base Salary, HRA, Allowance)
- View employee information
- Delete employees (soft delete - marks as inactive)
- View all active employees

### ✓ Salary Calculation
- **Gross Salary Calculation**: Base Salary + HRA + Allowance
- **Income Tax Calculation**: Progressive tax slabs
  - 5% up to ₹300,000
  - 10% from ₹300,001 to ₹500,000
  - 20% above ₹500,000
- **Provident Fund**: 12% of gross salary
- **Health Insurance**: Fixed amount of ₹500
- **Net Salary**: Gross Salary - Total Deductions

### ✓ Payslip Generation
- Generate detailed payslips for employees
- Monthly payslip tracking
- Prevent duplicate payslips for same month/year
- Formatted payslip printing
- Payslip history retrieval

### ✓ Reporting
- View individual employee payslips
- View payslips by month/year
- Monthly summary reports with totals
- Employee earnings and deductions breakdown

### ✓ Data Security
- Input validation for all user inputs
- Secure database connections
- Soft delete for employee records
- Error handling with meaningful messages
- Data integrity with foreign keys and unique constraints

## Project Structure

```
syntecxhub_employee_payroll_managemenet/
├── model/
│   ├── Employee.java           # Employee entity
│   └── Payslip.java            # Payslip entity
├── dao/
│   ├── EmployeeDAO.java        # Employee database operations
│   ├── PayrollDAO.java         # Payroll database operations
│   └── Payroll.java            # Legacy class (deprecated)
├── service/
│   └── PayrollService.java     # Business logic for payroll operations
├── util/
│   └── DBConnection.java       # Database connection utility
├── PayrollManagementSystem.java # Main application class (menu-driven)
└── database_schema.sql         # MySQL database schema
```

## Technology Stack

- **Language**: Java 8+
- **Database**: MySQL 5.7+
- **JDBC**: MySQL Connector/J
- **Build**: Maven (or compile manually)

## Installation & Setup

### Prerequisites
1. Java Development Kit (JDK 8 or higher)
2. MySQL Server (5.7 or higher)
3. MySQL JDBC Driver (mysql-connector-java-8.0.x or higher)

### Step 1: Database Setup

1. Open MySQL command line or MySQL Workbench
2. Execute the SQL script to create database and tables:
   ```bash
   mysql -u root -p < database_schema.sql
   ```
   
   Or manually run the script from `database_schema.sql`

3. Verify database creation:
   ```sql
   USE payroll_db;
   SHOW TABLES;
   ```

### Step 2: Configure Database Connection

Edit `util/DBConnection.java` if needed:
```java
private static final String URL = "jdbc:mysql://localhost:3306/payroll_db";
private static final String USER = "root";
private static final String PASSWORD = "root";
```

Update the password and username according to your MySQL configuration.

### Step 3: Add MySQL JDBC Driver

1. Download MySQL Connector/J from: https://dev.mysql.com/downloads/connector/j/
2. Add the JAR file to your project classpath
3. Or add to your IDE's library path

### Step 4: Compile the Project

```bash
# Navigate to project directory
cd syntecxhub_employee_payroll_managemenet

# Compile all Java files
javac -d . util/DBConnection.java model/Employee.java model/Payslip.java dao/EmployeeDAO.java dao/PayrollDAO.java dao/Payroll.java service/PayrollService.java PayrollManagementSystem.java
```

### Step 5: Run the Application

```bash
# Run the main application
java PayrollManagementSystem
```

## Usage Guide

### Main Menu Options

#### 1. Add New Employee
- Enter employee details (name, email, department, designation, join date)
- Enter salary components (base salary, HRA, allowance)
- System validates all inputs before storing

#### 2. View Employee Details
- Enter employee ID
- Display complete employee information including salary components

#### 3. Update Employee Salary
- Enter employee ID
- View current salary details
- Update base salary, HRA, and allowance
- Changes take effect for future payslips

#### 4. Delete Employee
- Enter employee ID
- Confirm deletion
- Employee marked as inactive (soft delete)

#### 5. View All Employees
- Display list of all active employees
- Shows ID, Name, Email, Department, and Designation

#### 6. Generate Payslip
- Enter employee ID, month (1-12), and year
- System calculates all salary components
- Payslip saved to database
- Detailed payslip displayed

#### 7. View Payslip
- Enter payslip ID
- Display complete payslip with all details

#### 8. View Employee Payslips
- Enter employee ID
- Display all payslips for the employee
- Shows summary of gross salary, deductions, and net salary

#### 9. View Payslips by Month
- Enter month (1-12) and year
- Display all payslips generated for that period
- Show monthly summary totals

#### 10. Exit
- Exit the application gracefully

## Example Workflow

```
1. Add Employee: Raj Kumar with base salary ₹75,000, HRA ₹15,000, Allowance ₹5,000
2. Generate Payslip: For January 2024
   - Gross Salary: ₹95,000
   - Income Tax (5%): ₹4,750
   - Provident Fund (12%): ₹11,400
   - Health Insurance: ₹500
   - Total Deductions: ₹16,650
   - Net Salary: ₹78,350
3. View Payslip: Display formatted payslip
4. Update Salary: New base salary ₹80,000
5. Generate Payslip: For February 2024 with new salary
```

## Database Schema

### Employees Table
```sql
emp_id (Primary Key, Auto Increment)
name (VARCHAR 100, NOT NULL)
email (VARCHAR 100, UNIQUE)
department (VARCHAR 50)
designation (VARCHAR 50)
join_date (DATE)
base_salary (DECIMAL 12,2)
hra (DECIMAL 12,2)
allowance (DECIMAL 12,2)
active (BOOLEAN, Default TRUE)
created_at (TIMESTAMP)
updated_at (TIMESTAMP)
```

### Payslips Table
```sql
payslip_id (Primary Key, Auto Increment)
emp_id (Foreign Key to employees.emp_id)
month (INT 1-12)
year (INT)
base_salary (DECIMAL 12,2)
hra (DECIMAL 12,2)
allowance (DECIMAL 12,2)
gross_salary (DECIMAL 12,2)
income_tax (DECIMAL 12,2)
provident_fund (DECIMAL 12,2)
health_insurance (DECIMAL 12,2)
total_deductions (DECIMAL 12,2)
net_salary (DECIMAL 12,2)
generated_date (DATE)
created_at (TIMESTAMP)
```

## Key Features Explained

### Accurate Calculations
- Progressive tax calculation based on Indian tax slabs
- Precise decimal handling for monetary values
- Rounding to 2 decimal places for currency

### Secure Data Handling
- Input validation and sanitization
- SQL prepared statements prevent SQL injection
- Foreign key constraints maintain data integrity
- Soft delete preserves historical data
- Audit trail for salary changes

### Error Handling
- Try-catch blocks for all database operations
- User-friendly error messages
- Validation for duplicate payslips
- Employee existence checks before operations

## Salary Calculation Formula

```
Gross Salary = Base Salary + HRA + Allowance

Income Tax = (Based on Progressive Slabs)
Provident Fund = Gross Salary × 12%
Health Insurance = ₹500 (Fixed)

Total Deductions = Income Tax + Provident Fund + Health Insurance

Net Salary = Gross Salary - Total Deductions
```

## Sample Test Data

The database schema includes sample employees:
1. Raj Kumar - Senior Developer - ₹75,000 base salary
2. Priya Singh - HR Manager - ₹60,000 base salary
3. Amit Patel - Finance Officer - ₹50,000 base salary
4. Neha Sharma - Operations Coordinator - ₹40,000 base salary

## Troubleshooting

### Issue: "MySQL Driver not found"
**Solution**: Ensure MySQL JDBC driver JAR is in classpath

### Issue: "Connection refused"
**Solution**: Verify MySQL server is running and correct host/port in DBConnection.java

### Issue: "Database payroll_db not found"
**Solution**: Execute database_schema.sql to create database

### Issue: "Access denied for user 'root'"
**Solution**: Update username and password in DBConnection.java to match your MySQL setup

### Issue: "Duplicate payslip for month/year"
**Solution**: Each employee can only have one payslip per month. Use View option to check existing payslips.

## Best Practices

1. **Always backup** your database before major operations
2. **Run sample data** generation to test functionality
3. **Validate inputs** for realistic salary data
4. **Review payslips** after generation
5. **Update salaries** at appropriate intervals
6. **Maintain audit logs** for compliance

## Future Enhancements

- [ ] Web interface (Spring Boot/JSP)
- [ ] Advanced reporting and analytics
- [ ] Email payslip delivery
- [ ] Employee self-service portal
- [ ] Leave management integration
- [ ] Attendance tracking
- [ ] Advance/Loan management
- [ ] Tax calculation improvements

## Support & Documentation

For issues or questions:
1. Check the troubleshooting section
2. Review error messages in console
3. Verify database connectivity
4. Check sample data insertion

## License

This project is for educational purposes.

## Author

Payroll Management System - Employee Payroll Solution
