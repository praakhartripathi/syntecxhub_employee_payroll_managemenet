package model;

public class Employee {
    private int empId;
    private String name;
    private String email;
    private String department;
    private String designation;
    private String joinDate;
    private double baseSalary;
    private double hra;
    private double allowance;
    private boolean active;

    public Employee() {
    }

    public Employee(int empId, String name, String email, String department, String designation, 
                    String joinDate, double baseSalary, double hra, double allowance) {
        this.empId = empId;
        this.name = name;
        this.email = email;
        this.department = department;
        this.designation = designation;
        this.joinDate = joinDate;
        this.baseSalary = baseSalary;
        this.hra = hra;
        this.allowance = allowance;
        this.active = true;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", department='" + department + '\'' +
                ", designation='" + designation + '\'' +
                ", joinDate='" + joinDate + '\'' +
                ", baseSalary=" + baseSalary +
                ", hra=" + hra +
                ", allowance=" + allowance +
                ", active=" + active +
                '}';
    }
}   