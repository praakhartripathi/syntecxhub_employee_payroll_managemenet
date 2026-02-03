#!/bin/bash

# Payroll Management System - Quick Setup Script
# This script helps set up the Payroll Management System

echo "================================================"
echo "  Payroll Management System - Setup Script"
echo "================================================"
echo ""

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Java is not installed. Please install Java 8 or higher."
    exit 1
fi

echo "✓ Java found: $(java -version 2>&1 | head -n 1)"
echo ""

# Check if MySQL is installed
if ! command -v mysql &> /dev/null; then
    echo "⚠️  MySQL command-line tool not found."
    echo "   Please ensure MySQL Server is running separately."
    echo ""
else
    echo "✓ MySQL found"
    echo ""
fi

# Display next steps
echo "================================================"
echo "NEXT STEPS:"
echo "================================================"
echo ""
echo "1. Database Setup:"
echo "   - Open MySQL and run:"
echo "     mysql -u root -p < database_schema.sql"
echo ""
echo "2. Configure Database Connection:"
echo "   - Edit util/DBConnection.java"
echo "   - Update URL, USER, PASSWORD if needed"
echo ""
echo "3. Add MySQL JDBC Driver:"
echo "   - Download from: https://dev.mysql.com/downloads/connector/j/"
echo "   - Add JAR to your classpath"
echo ""
echo "4. Compile Project:"
echo "   javac -d . util/DBConnection.java model/Employee.java model/Payslip.java dao/EmployeeDAO.java dao/PayrollDAO.java dao/Payroll.java service/PayrollService.java PayrollManagementSystem.java"
echo ""
echo "5. Run Application:"
echo "   java PayrollManagementSystem"
echo ""
echo "================================================"
echo ""
