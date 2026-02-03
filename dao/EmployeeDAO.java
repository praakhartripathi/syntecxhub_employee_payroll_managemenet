package dao;

import util.DBConnection;
import model.Employee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    
    /**
     * Add a new employee to the database
     */
    public boolean addEmployee(Employee emp) throws SQLException {
        String sql = "INSERT INTO employees(name, email, department, designation, join_date, base_salary, hra, allowance, active) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, emp.getName());
            ps.setString(2, emp.getEmail());
            ps.setString(3, emp.getDepartment());
            ps.setString(4, emp.getDesignation());
            ps.setDate(5, Date.valueOf(emp.getJoinDate()));
            ps.setDouble(6, emp.getBaseSalary());
            ps.setDouble(7, emp.getHra());
            ps.setDouble(8, emp.getAllowance());
            ps.setBoolean(9, emp.isActive());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error adding employee: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Update employee salary details
     */
    public boolean updateEmployeeSalary(int empId, double baseSalary, double hra, double allowance) throws SQLException {
        String sql = "UPDATE employees SET base_salary = ?, hra = ?, allowance = ? WHERE emp_id = ? AND active = true";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setDouble(1, baseSalary);
            ps.setDouble(2, hra);
            ps.setDouble(3, allowance);
            ps.setInt(4, empId);
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating salary: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Get employee by ID
     */
    public Employee getEmployeeById(int empId) throws SQLException {
        String sql = "SELECT * FROM employees WHERE emp_id = ? AND active = true";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToEmployee(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching employee: " + e.getMessage());
            throw e;
        }
        return null;
    }

    /**
     * Get all active employees
     */
    public List<Employee> getAllEmployees() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE active = true ORDER BY emp_id";
        
        try (Connection con = DBConnection.getConnection();
             Statement stmt = con.createStatement()) {
            
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                employees.add(mapResultSetToEmployee(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching employees: " + e.getMessage());
            throw e;
        }
        return employees;
    }

    /**
     * Soft delete employee (mark as inactive)
     */
    public boolean deleteEmployee(int empId) throws SQLException {
        String sql = "UPDATE employees SET active = false WHERE emp_id = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, empId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting employee: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Check if employee exists
     */
    public boolean employeeExists(int empId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM employees WHERE emp_id = ? AND active = true";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking employee existence: " + e.getMessage());
            throw e;
        }
        return false;
    }

    /**
     * Map ResultSet to Employee object
     */
    private Employee mapResultSetToEmployee(ResultSet rs) throws SQLException {
        Employee emp = new Employee();
        emp.setEmpId(rs.getInt("emp_id"));
        emp.setName(rs.getString("name"));
        emp.setEmail(rs.getString("email"));
        emp.setDepartment(rs.getString("department"));
        emp.setDesignation(rs.getString("designation"));
        emp.setJoinDate(rs.getDate("join_date").toString());
        emp.setBaseSalary(rs.getDouble("base_salary"));
        emp.setHra(rs.getDouble("hra"));
        emp.setAllowance(rs.getDouble("allowance"));
        emp.setActive(rs.getBoolean("active"));
        return emp;
    }
}
