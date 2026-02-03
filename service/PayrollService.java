package service;

import dao.EmployeeDAO;
import dao.PayrollDAO;
import model.Employee;
import model.Payslip;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PayrollService {
    
    private EmployeeDAO employeeDAO;
    private PayrollDAO payrollDAO;
    
    private static final double PF_PERCENTAGE = 12.0; // Provident Fund: 12% of gross salary
    private static final double HEALTH_INSURANCE = 500.0; // Fixed health insurance
    private static final double INCOME_TAX_SLAB_1 = 0.05; // 5% up to 300000
    private static final double INCOME_TAX_SLAB_2 = 0.10; // 10% from 300001 to 500000
    private static final double INCOME_TAX_SLAB_3 = 0.20; // 20% above 500000
    private static final double SLAB_1_LIMIT = 300000;
    private static final double SLAB_2_LIMIT = 500000;
    
    public PayrollService() {
        this.employeeDAO = new EmployeeDAO();
        this.payrollDAO = new PayrollDAO();
    }

    /**
     * Calculate gross salary
     */
    public double calculateGrossSalary(double baseSalary, double hra, double allowance) {
        if (baseSalary < 0 || hra < 0 || allowance < 0) {
            throw new IllegalArgumentException("Salary components cannot be negative");
        }
        return baseSalary + hra + allowance;
    }

    /**
     * Calculate provident fund (12% of gross salary)
     */
    public double calculateProvidentFund(double grossSalary) {
        return (grossSalary * PF_PERCENTAGE) / 100.0;
    }

    /**
     * Calculate income tax based on Indian tax slabs
     */
    public double calculateIncomeTax(double grossSalary) {
        double tax = 0.0;
        
        if (grossSalary <= SLAB_1_LIMIT) {
            tax = (grossSalary * INCOME_TAX_SLAB_1) / 100.0;
        } else if (grossSalary <= SLAB_2_LIMIT) {
            double slab1Amount = SLAB_1_LIMIT * INCOME_TAX_SLAB_1 / 100.0;
            double slab2Amount = (grossSalary - SLAB_1_LIMIT) * INCOME_TAX_SLAB_2 / 100.0;
            tax = slab1Amount + slab2Amount;
        } else {
            double slab1Amount = SLAB_1_LIMIT * INCOME_TAX_SLAB_1 / 100.0;
            double slab2Amount = (SLAB_2_LIMIT - SLAB_1_LIMIT) * INCOME_TAX_SLAB_2 / 100.0;
            double slab3Amount = (grossSalary - SLAB_2_LIMIT) * INCOME_TAX_SLAB_3 / 100.0;
            tax = slab1Amount + slab2Amount + slab3Amount;
        }
        
        return Math.round(tax * 100.0) / 100.0;
    }

    /**
     * Calculate total deductions
     */
    public double calculateTotalDeductions(double incomeTax, double providentFund, double healthInsurance) {
        return incomeTax + providentFund + healthInsurance;
    }

    /**
     * Calculate net salary
     */
    public double calculateNetSalary(double grossSalary, double totalDeductions) {
        if (totalDeductions > grossSalary) {
            throw new IllegalArgumentException("Total deductions cannot exceed gross salary");
        }
        double netSalary = grossSalary - totalDeductions;
        return Math.round(netSalary * 100.0) / 100.0;
    }

    /**
     * Generate payslip for an employee
     */
    public Payslip generatePayslip(int empId, int month, int year) throws SQLException {
        // Validate employee exists
        if (!employeeDAO.employeeExists(empId)) {
            throw new IllegalArgumentException("Employee with ID " + empId + " does not exist");
        }

        // Check if payslip already exists for this month/year
        if (payrollDAO.payslipExists(empId, month, year)) {
            throw new IllegalArgumentException("Payslip already exists for employee " + empId + " for month " + month + "/" + year);
        }

        // Get employee details
        Employee employee = employeeDAO.getEmployeeById(empId);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found");
        }

        // Create and populate payslip
        Payslip payslip = new Payslip(empId, month, year, employee.getBaseSalary(), 
                                       employee.getHra(), employee.getAllowance());

        // Calculate salary components
        double grossSalary = calculateGrossSalary(employee.getBaseSalary(), 
                                                   employee.getHra(), 
                                                   employee.getAllowance());
        double providentFund = calculateProvidentFund(grossSalary);
        double incomeTax = calculateIncomeTax(grossSalary);
        double totalDeductions = calculateTotalDeductions(incomeTax, providentFund, HEALTH_INSURANCE);
        double netSalary = calculateNetSalary(grossSalary, totalDeductions);

        // Set values in payslip
        payslip.setGrossSalary(grossSalary);
        payslip.setProvidentFund(providentFund);
        payslip.setIncomeTax(incomeTax);
        payslip.setHealthInsurance(HEALTH_INSURANCE);
        payslip.setTotalDeductions(totalDeductions);
        payslip.setNetSalary(netSalary);
        payslip.setGeneratedDate(LocalDate.now());

        // Save payslip to database
        payrollDAO.savePayslip(payslip);
        return payslip;
    }

    /**
     * Get payslip details by payslip ID
     */
    public Payslip getPayslipDetails(int payslipId) throws SQLException {
        return payrollDAO.getPayslipById(payslipId);
    }

    /**
     * Get all payslips for an employee
     */
    public List<Payslip> getEmployeePayslips(int empId) throws SQLException {
        if (!employeeDAO.employeeExists(empId)) {
            throw new IllegalArgumentException("Employee with ID " + empId + " does not exist");
        }
        return payrollDAO.getPayslipsByEmployeeId(empId);
    }

    /**
     * Get payslips generated for a specific month and year
     */
    public List<Payslip> getPayslipsByMonth(int month, int year) throws SQLException {
        if (month < 1 || month > 12) {
            throw new IllegalArgumentException("Month must be between 1 and 12");
        }
        return payrollDAO.getPayslipsByMonthAndYear(month, year);
    }

    /**
     * Update employee salary details
     */
    public boolean updateEmployeeSalary(int empId, double baseSalary, double hra, double allowance) throws SQLException {
        if (baseSalary < 0 || hra < 0 || allowance < 0) {
            throw new IllegalArgumentException("Salary components cannot be negative");
        }
        
        if (!employeeDAO.employeeExists(empId)) {
            throw new IllegalArgumentException("Employee with ID " + empId + " does not exist");
        }
        
        return employeeDAO.updateEmployeeSalary(empId, baseSalary, hra, allowance);
    }

    /**
     * Get employee by ID
     */
    public Employee getEmployeeDetails(int empId) throws SQLException {
        return employeeDAO.getEmployeeById(empId);
    }

    /**
     * Add new employee
     */
    public boolean addEmployee(Employee employee) throws SQLException {
        if (employee == null || employee.getName() == null || employee.getName().isEmpty()) {
            throw new IllegalArgumentException("Employee name cannot be empty");
        }
        
        if (employee.getBaseSalary() < 0) {
            throw new IllegalArgumentException("Base salary cannot be negative");
        }
        
        return employeeDAO.addEmployee(employee);
    }

    /**
     * Get all employees
     */
    public List<Employee> getAllEmployees() throws SQLException {
        return employeeDAO.getAllEmployees();
    }

    /**
     * Delete employee (soft delete - mark as inactive)
     */
    public boolean deleteEmployee(int empId) throws SQLException {
        if (!employeeDAO.employeeExists(empId)) {
            throw new IllegalArgumentException("Employee with ID " + empId + " does not exist");
        }
        return employeeDAO.deleteEmployee(empId);
    }

    /**
     * Print payslip details in a formatted manner
     */
    public void printPayslip(Payslip payslip) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n================== PAYSLIP ==================\n");
        sb.append("Payslip ID: ").append(payslip.getPayslipId()).append("\n");
        sb.append("Employee ID: ").append(payslip.getEmpId()).append("\n");
        sb.append("Month/Year: ").append(payslip.getMonth()).append("/").append(payslip.getYear()).append("\n");
        sb.append("Generated Date: ").append(payslip.getGeneratedDate()).append("\n");
        sb.append("-------------------------------------------\n");
        sb.append("EARNINGS:\n");
        sb.append("Base Salary: ₹ ").append(String.format("%.2f", payslip.getBaseSalary())).append("\n");
        sb.append("HRA: ₹ ").append(String.format("%.2f", payslip.getHra())).append("\n");
        sb.append("Allowance: ₹ ").append(String.format("%.2f", payslip.getAllowance())).append("\n");
        sb.append("-------------------------------------------\n");
        sb.append("Gross Salary: ₹ ").append(String.format("%.2f", payslip.getGrossSalary())).append("\n");
        sb.append("-------------------------------------------\n");
        sb.append("DEDUCTIONS:\n");
        sb.append("Income Tax: ₹ ").append(String.format("%.2f", payslip.getIncomeTax())).append("\n");
        sb.append("Provident Fund (12%): ₹ ").append(String.format("%.2f", payslip.getProvidentFund())).append("\n");
        sb.append("Health Insurance: ₹ ").append(String.format("%.2f", payslip.getHealthInsurance())).append("\n");
        sb.append("-------------------------------------------\n");
        sb.append("Total Deductions: ₹ ").append(String.format("%.2f", payslip.getTotalDeductions())).append("\n");
        sb.append("-------------------------------------------\n");
        sb.append("NET SALARY: ₹ ").append(String.format("%.2f", payslip.getNetSalary())).append("\n");
        sb.append("==========================================\n");
        
        System.out.println(sb.toString());
    }
}
