import service.PayrollService;
import model.Employee;
import model.Payslip;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Scanner;

public class PayrollManagementSystem {
    
    private PayrollService payrollService;
    private Scanner scanner;
    
    public PayrollManagementSystem() {
        this.payrollService = new PayrollService();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Main menu display
     */
    private void displayMainMenu() {
        System.out.println("\n========== PAYROLL MANAGEMENT SYSTEM ==========");
        System.out.println("1. Add New Employee");
        System.out.println("2. View Employee Details");
        System.out.println("3. Update Employee Salary");
        System.out.println("4. Delete Employee");
        System.out.println("5. View All Employees");
        System.out.println("6. Generate Payslip");
        System.out.println("7. View Payslip");
        System.out.println("8. View Employee Payslips");
        System.out.println("9. View Payslips by Month");
        System.out.println("10. Exit");
        System.out.println("==============================================");
        System.out.print("Enter your choice: ");
    }

    /**
     * Add new employee
     */
    private void addNewEmployee() {
        try {
            System.out.println("\n--- Add New Employee ---");
            
            System.out.print("Enter Name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                System.out.println("Error: Name cannot be empty");
                return;
            }
            
            System.out.print("Enter Email: ");
            String email = scanner.nextLine().trim();
            if (!email.contains("@")) {
                System.out.println("Error: Invalid email format");
                return;
            }
            
            System.out.print("Enter Department: ");
            String department = scanner.nextLine().trim();
            
            System.out.print("Enter Designation: ");
            String designation = scanner.nextLine().trim();
            
            System.out.print("Enter Join Date (YYYY-MM-DD): ");
            String joinDate = scanner.nextLine().trim();
            
            System.out.print("Enter Base Salary: ");
            double baseSalary = getPositiveDouble();
            
            System.out.print("Enter HRA: ");
            double hra = getPositiveDouble();
            
            System.out.print("Enter Allowance: ");
            double allowance = getPositiveDouble();
            
            Employee employee = new Employee(0, name, email, department, designation, joinDate, baseSalary, hra, allowance);
            
            if (payrollService.addEmployee(employee)) {
                System.out.println("✓ Employee added successfully!");
            } else {
                System.out.println("✗ Failed to add employee");
            }
        } catch (SQLException e) {
            System.out.println("✗ Database Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    /**
     * View employee details
     */
    private void viewEmployeeDetails() {
        try {
            System.out.println("\n--- View Employee Details ---");
            System.out.print("Enter Employee ID: ");
            int empId = getPositiveInt();
            
            Employee employee = payrollService.getEmployeeDetails(empId);
            if (employee != null) {
                System.out.println("\nEmployee Details:");
                System.out.println("ID: " + employee.getEmpId());
                System.out.println("Name: " + employee.getName());
                System.out.println("Email: " + employee.getEmail());
                System.out.println("Department: " + employee.getDepartment());
                System.out.println("Designation: " + employee.getDesignation());
                System.out.println("Join Date: " + employee.getJoinDate());
                System.out.println("Base Salary: ₹ " + String.format("%.2f", employee.getBaseSalary()));
                System.out.println("HRA: ₹ " + String.format("%.2f", employee.getHra()));
                System.out.println("Allowance: ₹ " + String.format("%.2f", employee.getAllowance()));
            } else {
                System.out.println("✗ Employee not found");
            }
        } catch (SQLException e) {
            System.out.println("✗ Database Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    /**
     * Update employee salary
     */
    private void updateEmployeeSalary() {
        try {
            System.out.println("\n--- Update Employee Salary ---");
            System.out.print("Enter Employee ID: ");
            int empId = getPositiveInt();
            
            // Check if employee exists
            Employee employee = payrollService.getEmployeeDetails(empId);
            if (employee == null) {
                System.out.println("✗ Employee not found");
                return;
            }
            
            System.out.println("\nCurrent Salary Details:");
            System.out.println("Base Salary: ₹ " + String.format("%.2f", employee.getBaseSalary()));
            System.out.println("HRA: ₹ " + String.format("%.2f", employee.getHra()));
            System.out.println("Allowance: ₹ " + String.format("%.2f", employee.getAllowance()));
            
            System.out.print("\nEnter New Base Salary: ");
            double baseSalary = getPositiveDouble();
            
            System.out.print("Enter New HRA: ");
            double hra = getPositiveDouble();
            
            System.out.print("Enter New Allowance: ");
            double allowance = getPositiveDouble();
            
            if (payrollService.updateEmployeeSalary(empId, baseSalary, hra, allowance)) {
                System.out.println("✓ Salary updated successfully!");
            } else {
                System.out.println("✗ Failed to update salary");
            }
        } catch (SQLException e) {
            System.out.println("✗ Database Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    /**
     * Delete employee
     */
    private void deleteEmployee() {
        try {
            System.out.println("\n--- Delete Employee ---");
            System.out.print("Enter Employee ID: ");
            int empId = getPositiveInt();
            
            System.out.print("Are you sure you want to delete this employee? (yes/no): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            
            if ("yes".equals(confirm)) {
                if (payrollService.deleteEmployee(empId)) {
                    System.out.println("✓ Employee deleted successfully!");
                } else {
                    System.out.println("✗ Failed to delete employee");
                }
            } else {
                System.out.println("Operation cancelled");
            }
        } catch (SQLException e) {
            System.out.println("✗ Database Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    /**
     * View all employees
     */
    private void viewAllEmployees() {
        try {
            System.out.println("\n--- All Employees ---");
            List<Employee> employees = payrollService.getAllEmployees();
            
            if (employees.isEmpty()) {
                System.out.println("No employees found");
                return;
            }
            
            System.out.printf("%-5s %-20s %-20s %-15s %-15s %n", "ID", "Name", "Email", "Department", "Designation");
            System.out.println("========================================================================================");
            
            for (Employee emp : employees) {
                System.out.printf("%-5d %-20s %-20s %-15s %-15s %n", 
                    emp.getEmpId(), 
                    emp.getName().substring(0, Math.min(20, emp.getName().length())),
                    emp.getEmail().substring(0, Math.min(20, emp.getEmail().length())),
                    emp.getDepartment(),
                    emp.getDesignation());
            }
        } catch (SQLException e) {
            System.out.println("✗ Database Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    /**
     * Generate payslip
     */
    private void generatePayslip() {
        try {
            System.out.println("\n--- Generate Payslip ---");
            System.out.print("Enter Employee ID: ");
            int empId = getPositiveInt();
            
            System.out.print("Enter Month (1-12): ");
            int month = getPositiveInt();
            if (month < 1 || month > 12) {
                System.out.println("✗ Invalid month");
                return;
            }
            
            System.out.print("Enter Year: ");
            int year = getPositiveInt();
            
            Payslip payslip = payrollService.generatePayslip(empId, month, year);
            System.out.println("✓ Payslip generated successfully!");
            payrollService.printPayslip(payslip);
        } catch (SQLException e) {
            System.out.println("✗ Database Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("✗ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    /**
     * View payslip by ID
     */
    private void viewPayslip() {
        try {
            System.out.println("\n--- View Payslip ---");
            System.out.print("Enter Payslip ID: ");
            int payslipId = getPositiveInt();
            
            Payslip payslip = payrollService.getPayslipDetails(payslipId);
            if (payslip != null) {
                payrollService.printPayslip(payslip);
            } else {
                System.out.println("✗ Payslip not found");
            }
        } catch (SQLException e) {
            System.out.println("✗ Database Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    /**
     * View all payslips for an employee
     */
    private void viewEmployeePayslips() {
        try {
            System.out.println("\n--- View Employee Payslips ---");
            System.out.print("Enter Employee ID: ");
            int empId = getPositiveInt();
            
            List<Payslip> payslips = payrollService.getEmployeePayslips(empId);
            
            if (payslips.isEmpty()) {
                System.out.println("No payslips found for this employee");
                return;
            }
            
            System.out.println("\nPayslips for Employee " + empId + ":");
            System.out.printf("%-10s %-8s %-8s %-12s %-12s %-12s %n", "Payslip ID", "Month", "Year", "Gross", "Deductions", "Net");
            System.out.println("===============================================================");
            
            for (Payslip p : payslips) {
                System.out.printf("%-10d %-8d %-8d ₹%-11.2f ₹%-11.2f ₹%-11.2f %n", 
                    p.getPayslipId(), p.getMonth(), p.getYear(), p.getGrossSalary(), 
                    p.getTotalDeductions(), p.getNetSalary());
            }
        } catch (SQLException e) {
            System.out.println("✗ Database Error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("✗ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    /**
     * View payslips by month
     */
    private void viewPayslipsByMonth() {
        try {
            System.out.println("\n--- View Payslips by Month ---");
            System.out.print("Enter Month (1-12): ");
            int month = getPositiveInt();
            if (month < 1 || month > 12) {
                System.out.println("✗ Invalid month");
                return;
            }
            
            System.out.print("Enter Year: ");
            int year = getPositiveInt();
            
            List<Payslip> payslips = payrollService.getPayslipsByMonth(month, year);
            
            if (payslips.isEmpty()) {
                System.out.println("No payslips found for this month");
                return;
            }
            
            System.out.println("\nPayslips for " + month + "/" + year + ":");
            System.out.printf("%-10s %-8s %-12s %-12s %-12s %n", "Payslip ID", "Emp ID", "Gross", "Deductions", "Net");
            System.out.println("====================================================");
            
            double totalGross = 0, totalDeductions = 0, totalNet = 0;
            
            for (Payslip p : payslips) {
                System.out.printf("%-10d %-8d ₹%-11.2f ₹%-11.2f ₹%-11.2f %n", 
                    p.getPayslipId(), p.getEmpId(), p.getGrossSalary(), 
                    p.getTotalDeductions(), p.getNetSalary());
                totalGross += p.getGrossSalary();
                totalDeductions += p.getTotalDeductions();
                totalNet += p.getNetSalary();
            }
            
            System.out.println("====================================================");
            System.out.printf("%-10s %-8s ₹%-11.2f ₹%-11.2f ₹%-11.2f %n", "TOTAL", "", totalGross, totalDeductions, totalNet);
        } catch (SQLException e) {
            System.out.println("✗ Database Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }
    }

    /**
     * Get positive integer input
     */
    private int getPositiveInt() {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value > 0) {
                    return value;
                } else {
                    System.out.print("Please enter a positive number: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    /**
     * Get positive double input
     */
    private double getPositiveDouble() {
        while (true) {
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value >= 0) {
                    return value;
                } else {
                    System.out.print("Please enter a non-negative number: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
            }
        }
    }

    /**
     * Main application loop
     */
    public void run() {
        System.out.println("\n╔════════════════════════════════════════════════╗");
        System.out.println("║   WELCOME TO PAYROLL MANAGEMENT SYSTEM        ║");
        System.out.println("╚════════════════════════════════════════════════╝");
        
        boolean running = true;
        
        while (running) {
            displayMainMenu();
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    addNewEmployee();
                    break;
                case "2":
                    viewEmployeeDetails();
                    break;
                case "3":
                    updateEmployeeSalary();
                    break;
                case "4":
                    deleteEmployee();
                    break;
                case "5":
                    viewAllEmployees();
                    break;
                case "6":
                    generatePayslip();
                    break;
                case "7":
                    viewPayslip();
                    break;
                case "8":
                    viewEmployeePayslips();
                    break;
                case "9":
                    viewPayslipsByMonth();
                    break;
                case "10":
                    running = false;
                    System.out.println("\nThank you for using Payroll Management System. Goodbye!");
                    break;
                default:
                    System.out.println("✗ Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }

    /**
     * Application entry point
     */
    public static void main(String[] args) {
        PayrollManagementSystem app = new PayrollManagementSystem();
        app.run();
    }
}
