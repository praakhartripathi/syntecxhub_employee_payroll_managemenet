package model;

import java.time.LocalDate;

public class Payslip {
    private int payslipId;
    private int empId;
    private int month;
    private int year;
    private double baseSalary;
    private double hra;
    private double allowance;
    private double grossSalary;
    private double incomeTax;
    private double providentFund;
    private double healthInsurance;
    private double totalDeductions;
    private double netSalary;
    private LocalDate generatedDate;

    public Payslip() {
    }

    public Payslip(int empId, int month, int year, double baseSalary, double hra, double allowance) {
        this.empId = empId;
        this.month = month;
        this.year = year;
        this.baseSalary = baseSalary;
        this.hra = hra;
        this.allowance = allowance;
        this.generatedDate = LocalDate.now();
    }

    // Getters and Setters
    public int getPayslipId() {
        return payslipId;
    }

    public void setPayslipId(int payslipId) {
        this.payslipId = payslipId;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public double getHra() {
        return hra;
    }

    public void setHra(double hra) {
        this.hra = hra;
    }

    public double getAllowance() {
        return allowance;
    }

    public void setAllowance(double allowance) {
        this.allowance = allowance;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public double getIncomeTax() {
        return incomeTax;
    }

    public void setIncomeTax(double incomeTax) {
        this.incomeTax = incomeTax;
    }

    public double getProvidentFund() {
        return providentFund;
    }

    public void setProvidentFund(double providentFund) {
        this.providentFund = providentFund;
    }

    public double getHealthInsurance() {
        return healthInsurance;
    }

    public void setHealthInsurance(double healthInsurance) {
        this.healthInsurance = healthInsurance;
    }

    public double getTotalDeductions() {
        return totalDeductions;
    }

    public void setTotalDeductions(double totalDeductions) {
        this.totalDeductions = totalDeductions;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public LocalDate getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(LocalDate generatedDate) {
        this.generatedDate = generatedDate;
    }

    @Override
    public String toString() {
        return "Payslip{" +
                "payslipId=" + payslipId +
                ", empId=" + empId +
                ", month=" + month +
                ", year=" + year +
                ", grossSalary=" + grossSalary +
                ", totalDeductions=" + totalDeductions +
                ", netSalary=" + netSalary +
                ", generatedDate=" + generatedDate +
                '}';
    }
}
