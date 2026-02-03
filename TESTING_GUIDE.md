# Testing Guide for Payroll Management System

## Unit Testing Scenarios

### Test Case 1: Gross Salary Calculation
**Purpose**: Verify accurate gross salary calculation
**Input**: Base Salary: 50000, HRA: 10000, Allowance: 5000
**Expected Output**: 65000
**Steps**:
1. Create PayrollService instance
2. Call calculateGrossSalary(50000, 10000, 5000)
3. Assert result equals 65000

### Test Case 2: Income Tax Calculation - Slab 1
**Purpose**: Test tax calculation for salary ≤ 300000
**Input**: Gross Salary: 250000
**Expected Output**: 12500 (5% of 250000)
**Steps**:
1. Create PayrollService instance
2. Call calculateIncomeTax(250000)
3. Assert result equals 12500

### Test Case 3: Income Tax Calculation - Slab 2
**Purpose**: Test tax calculation for salary between 300001-500000
**Input**: Gross Salary: 400000
**Expected Output**: 15000 + 10000 = 25000
**Calculation**: (300000 × 5%) + (100000 × 10%) = 15000 + 10000
**Steps**:
1. Create PayrollService instance
2. Call calculateIncomeTax(400000)
3. Assert result equals 25000

### Test Case 4: Income Tax Calculation - Slab 3
**Purpose**: Test tax calculation for salary > 500000
**Input**: Gross Salary: 600000
**Expected Output**: 15000 + 20000 + 20000 = 55000
**Calculation**: (300000 × 5%) + (200000 × 10%) + (100000 × 20%)
**Steps**:
1. Create PayrollService instance
2. Call calculateIncomeTax(600000)
3. Assert result equals 55000

### Test Case 5: Provident Fund Calculation
**Purpose**: Verify PF calculation (12% of gross salary)
**Input**: Gross Salary: 100000
**Expected Output**: 12000
**Steps**:
1. Create PayrollService instance
2. Call calculateProvidentFund(100000)
3. Assert result equals 12000

### Test Case 6: Net Salary Calculation
**Purpose**: Verify net salary calculation
**Input**: Gross Salary: 100000, Total Deductions: 25000
**Expected Output**: 75000
**Steps**:
1. Create PayrollService instance
2. Call calculateNetSalary(100000, 25000)
3. Assert result equals 75000

### Test Case 7: Invalid Input - Negative Salary
**Purpose**: Validate error handling for negative values
**Input**: Gross Salary: -50000
**Expected Output**: IllegalArgumentException
**Steps**:
1. Create PayrollService instance
2. Call calculateGrossSalary(-50000, 10000, 5000)
3. Assert exception is thrown

### Test Case 8: Invalid Input - Deductions > Gross
**Purpose**: Validate error handling for invalid deductions
**Input**: Gross Salary: 50000, Total Deductions: 60000
**Expected Output**: IllegalArgumentException
**Steps**:
1. Create PayrollService instance
2. Call calculateNetSalary(50000, 60000)
3. Assert exception is thrown

## Integration Testing Scenarios

### Test Scenario 1: Complete Employee Addition & Payslip Generation

**Precondition**: Database is running and initialized

**Steps**:
1. Add new employee:
   - Name: Test Employee
   - Email: test@example.com
   - Department: IT
   - Designation: Developer
   - Join Date: 2024-01-01
   - Base Salary: 50000
   - HRA: 10000
   - Allowance: 5000

2. Verify employee is saved:
   - Query database for employee
   - Confirm all details are saved

3. Generate payslip for January 2024:
   - Call generatePayslip(empId, 1, 2024)
   - Verify payslip is created

4. Verify payslip calculations:
   - Gross Salary: 65000
   - Income Tax: 3250 (5% of 65000)
   - PF: 7800 (12% of 65000)
   - Health Insurance: 500
   - Total Deductions: 11550
   - Net Salary: 53450

5. Verify payslip is stored in database

### Test Scenario 2: Salary Update & New Payslip

**Precondition**: Employee exists in system

**Steps**:
1. Get current employee details
2. Update salary:
   - New Base Salary: 60000
   - New HRA: 12000
   - New Allowance: 6000
3. Generate payslip for February 2024:
   - New Gross Salary: 78000
   - New tax calculations applied
4. Verify old and new payslips exist

### Test Scenario 3: Multiple Employees Monthly Report

**Precondition**: Multiple employees with payslips for same month

**Steps**:
1. Add 3 employees with different salaries
2. Generate payslips for all for January 2024
3. Query payslips for January 2024
4. Verify totals:
   - Total Gross Salary = Sum of all gross salaries
   - Total Deductions = Sum of all deductions
   - Total Net Salary = Sum of all net salaries
5. Display monthly report

### Test Scenario 4: Duplicate Payslip Prevention

**Precondition**: Employee with existing payslip for January 2024

**Steps**:
1. Attempt to generate payslip for same employee, same month/year
2. Verify IllegalArgumentException is thrown
3. Verify error message indicates duplicate

## Manual Testing Checklist

### Employee Management Tests
- [ ] Add employee with valid data → Successfully saved
- [ ] Add employee with invalid email → Error message shown
- [ ] Add employee with negative salary → Error message shown
- [ ] View existing employee → All details displayed correctly
- [ ] View non-existent employee → "Not found" message
- [ ] Update employee salary → New values applied
- [ ] Delete employee → Marked as inactive
- [ ] View all employees → All active employees listed

### Payslip Generation Tests
- [ ] Generate payslip for valid employee → Payslip created successfully
- [ ] Generate payslip for non-existent employee → Error message
- [ ] Generate duplicate payslip → Error message about duplicate
- [ ] Generate payslip for month > 12 → Error message
- [ ] Generate payslip with negative month → Error message
- [ ] View generated payslip → All calculations correct
- [ ] View payslip history → All previous payslips listed

### Calculation Tests
- [ ] Low salary (< 300000) → Correct tax slab 1 applied
- [ ] Medium salary (300001-500000) → Correct tax slab 2 applied
- [ ] High salary (> 500000) → Correct tax slab 3 applied
- [ ] Gross salary = Base + HRA + Allowance
- [ ] PF = 12% of Gross Salary
- [ ] Net Salary = Gross - All Deductions
- [ ] Currency precision to 2 decimal places

### Database Tests
- [ ] Data persists after application restart
- [ ] Foreign key constraints enforced
- [ ] Unique constraints prevent duplicates
- [ ] Soft delete prevents hard data loss
- [ ] Audit logs updated on salary changes

### Error Handling Tests
- [ ] Invalid database connection → Meaningful error
- [ ] Missing required fields → Validation error
- [ ] Invalid date format → Error message
- [ ] Special characters in input → Handled correctly
- [ ] Large salary values → Calculated accurately
- [ ] Decimal precision maintained → No rounding errors

## Performance Testing

### Load Test: 1000 Employee Payslips Generation
- Measure time to generate 1000 payslips
- Monitor database connections
- Check memory usage
- Verify all payslips calculated correctly

### Load Test: Large Dataset Retrieval
- Query 10000 payslips
- Measure response time
- Verify pagination if implemented

## Database Integrity Tests

### Test 1: Foreign Key Constraint
1. Create payslip with non-existent emp_id
2. Verify database rejects operation

### Test 2: Unique Constraint
1. Insert duplicate payslip for same employee, month, year
2. Verify database rejects operation

### Test 3: Data Type Validation
1. Insert non-numeric value in salary field
2. Verify database rejects operation

## Sample Test Data Sets

### Dataset 1: Salary Range Testing
```
Employee 1: Base 100000 (Below Slab 1 limit)
Employee 2: Base 400000 (In Slab 2)
Employee 3: Base 600000 (Above Slab 2)
Employee 4: Base 250000 (Edge of Slab 1)
Employee 5: Base 500000 (Edge of Slab 2)
```

### Dataset 2: Edge Cases
```
Employee 1: Base 0
Employee 2: Base 1
Employee 3: Base 9999999.99
Employee 4: Very long name (255 characters)
Employee 5: Special characters in email
```

## Expected Payslip Output Format

```
================== PAYSLIP ==================
Payslip ID: 1
Employee ID: 101
Month/Year: 1/2024
Generated Date: 2024-02-01
-------------------------------------------
EARNINGS:
Base Salary: ₹ 50000.00
HRA: ₹ 10000.00
Allowance: ₹ 5000.00
-------------------------------------------
Gross Salary: ₹ 65000.00
-------------------------------------------
DEDUCTIONS:
Income Tax: ₹ 3250.00
Provident Fund (12%): ₹ 7800.00
Health Insurance: ₹ 500.00
-------------------------------------------
Total Deductions: ₹ 11550.00
-------------------------------------------
NET SALARY: ₹ 53450.00
==========================================
```

## Verification Checklist

- [ ] All calculations mathematically correct
- [ ] Data persists in database
- [ ] No SQL injection vulnerabilities
- [ ] Input validation working
- [ ] Error messages are user-friendly
- [ ] Performance acceptable (< 2 sec per operation)
- [ ] No memory leaks
- [ ] Concurrent operations handled safely
- [ ] Dates handled correctly
- [ ] Currency values precise
