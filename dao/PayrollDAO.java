package dao;

import util.DBConnection;
import model.Payslip;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PayrollDAO {
    
    /**
     * Save payslip to database
     */
    public boolean savePayslip(Payslip payslip) throws SQLException {
        String sql = "INSERT INTO payslips(emp_id, month, year, base_salary, hra, allowance, gross_salary, income_tax, provident_fund, health_insurance, total_deductions, net_salary, generated_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, payslip.getEmpId());
            ps.setInt(2, payslip.getMonth());
            ps.setInt(3, payslip.getYear());
            ps.setDouble(4, payslip.getBaseSalary());
            ps.setDouble(5, payslip.getHra());
            ps.setDouble(6, payslip.getAllowance());
            ps.setDouble(7, payslip.getGrossSalary());
            ps.setDouble(8, payslip.getIncomeTax());
            ps.setDouble(9, payslip.getProvidentFund());
            ps.setDouble(10, payslip.getHealthInsurance());
            ps.setDouble(11, payslip.getTotalDeductions());
            ps.setDouble(12, payslip.getNetSalary());
            ps.setDate(13, Date.valueOf(payslip.getGeneratedDate()));
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error saving payslip: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Get payslip by ID
     */
    public Payslip getPayslipById(int payslipId) throws SQLException {
        String sql = "SELECT * FROM payslips WHERE payslip_id = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, payslipId);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToPayslip(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching payslip: " + e.getMessage());
            throw e;
        }
        return null;
    }

    /**
     * Get all payslips for an employee
     */
    public List<Payslip> getPayslipsByEmployeeId(int empId) throws SQLException {
        List<Payslip> payslips = new ArrayList<>();
        String sql = "SELECT * FROM payslips WHERE emp_id = ? ORDER BY year DESC, month DESC";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                payslips.add(mapResultSetToPayslip(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching payslips: " + e.getMessage());
            throw e;
        }
        return payslips;
    }

    /**
     * Get payslips for a specific month and year
     */
    public List<Payslip> getPayslipsByMonthAndYear(int month, int year) throws SQLException {
        List<Payslip> payslips = new ArrayList<>();
        String sql = "SELECT * FROM payslips WHERE month = ? AND year = ? ORDER BY emp_id";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, month);
            ps.setInt(2, year);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                payslips.add(mapResultSetToPayslip(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching payslips by month and year: " + e.getMessage());
            throw e;
        }
        return payslips;
    }

    /**
     * Check if payslip already exists for employee in a specific month/year
     */
    public boolean payslipExists(int empId, int month, int year) throws SQLException {
        String sql = "SELECT COUNT(*) FROM payslips WHERE emp_id = ? AND month = ? AND year = ?";
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, empId);
            ps.setInt(2, month);
            ps.setInt(3, year);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking payslip existence: " + e.getMessage());
            throw e;
        }
        return false;
    }

    /**
     * Map ResultSet to Payslip object
     */
    private Payslip mapResultSetToPayslip(ResultSet rs) throws SQLException {
        Payslip payslip = new Payslip();
        payslip.setPayslipId(rs.getInt("payslip_id"));
        payslip.setEmpId(rs.getInt("emp_id"));
        payslip.setMonth(rs.getInt("month"));
        payslip.setYear(rs.getInt("year"));
        payslip.setBaseSalary(rs.getDouble("base_salary"));
        payslip.setHra(rs.getDouble("hra"));
        payslip.setAllowance(rs.getDouble("allowance"));
        payslip.setGrossSalary(rs.getDouble("gross_salary"));
        payslip.setIncomeTax(rs.getDouble("income_tax"));
        payslip.setProvidentFund(rs.getDouble("provident_fund"));
        payslip.setHealthInsurance(rs.getDouble("health_insurance"));
        payslip.setTotalDeductions(rs.getDouble("total_deductions"));
        payslip.setNetSalary(rs.getDouble("net_salary"));
        payslip.setGeneratedDate(rs.getDate("generated_date").toLocalDate());
        return payslip;
    }
}
