# Installation and Configuration Guide

## System Requirements

### Minimum Requirements
- Java Development Kit (JDK) 8 or higher
- MySQL Server 5.7 or higher
- 512 MB RAM
- 100 MB disk space

### Recommended Requirements
- Java Development Kit (JDK) 11 or higher
- MySQL Server 8.0 or higher
- 2 GB RAM
- 500 MB disk space

## Windows Installation

### Step 1: Install Java

1. Download JDK from: https://www.oracle.com/java/technologies/downloads/
2. Run the installer and follow on-screen instructions
3. Set JAVA_HOME environment variable:
   - Right-click "This PC" → Properties
   - Click "Advanced system settings"
   - Click "Environment Variables"
   - Click "New" under "System variables"
   - Variable name: `JAVA_HOME`
   - Variable value: `C:\Program Files\Java\jdk-11` (or your JDK path)
4. Verify installation:
   ```cmd
   java -version
   ```

### Step 2: Install MySQL

1. Download MySQL from: https://dev.mysql.com/downloads/mysql/
2. Run the installer
3. Choose setup type (Developer Default recommended)
4. Configure MySQL Server:
   - Port: 3306 (default)
   - MySQL Root Password: Set a strong password
5. Configure MySQL as Windows Service
6. Verify installation:
   ```cmd
   mysql --version
   ```

### Step 3: Download MySQL JDBC Driver

1. Go to: https://dev.mysql.com/downloads/connector/j/
2. Download mysql-connector-java-8.0.x.jar
3. Save to a known location (e.g., `C:\mysql-connector`)

### Step 4: Clone/Download Project

```cmd
git clone <repository-url>
cd syntecxhub_employee_payroll_managemenet
```

### Step 5: Set Up Database

1. Open Command Prompt
2. Connect to MySQL:
   ```cmd
   mysql -u root -p
   ```
3. Enter your root password
4. Run the SQL script:
   ```sql
   source database_schema.sql;
   ```

### Step 6: Configure Java Project

1. Edit `util/DBConnection.java`:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/payroll_db";
   private static final String USER = "root";
   private static final String PASSWORD = "your_password_here";
   ```

2. Add JDBC driver to classpath when compiling:
   ```cmd
   set CLASSPATH=%CLASSPATH%;C:\mysql-connector\mysql-connector-java-8.0.x.jar
   ```

### Step 7: Compile Project

```cmd
cd syntecxhub_employee_payroll_managemenet
javac -cp C:\mysql-connector\mysql-connector-java-8.0.x.jar -d . util/DBConnection.java model/Employee.java model/Payslip.java dao/EmployeeDAO.java dao/PayrollDAO.java dao/Payroll.java service/PayrollService.java PayrollManagementSystem.java
```

### Step 8: Run Application

```cmd
java -cp .;C:\mysql-connector\mysql-connector-java-8.0.x.jar PayrollManagementSystem
```

---

## Linux/Mac Installation

### Step 1: Install Java

#### Ubuntu/Debian:
```bash
sudo apt-get update
sudo apt-get install openjdk-11-jdk
java -version
```

#### macOS:
```bash
brew install openjdk@11
java -version
```

### Step 2: Install MySQL

#### Ubuntu/Debian:
```bash
sudo apt-get install mysql-server mysql-client
sudo mysql_secure_installation
```

#### macOS:
```bash
brew install mysql
brew services start mysql
mysql_secure_installation
```

### Step 3: Download MySQL JDBC Driver

```bash
cd ~
wget https://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-8.0.x.tar.gz
tar -xzf mysql-connector-java-8.0.x.tar.gz
```

### Step 4: Clone/Download Project

```bash
git clone <repository-url>
cd syntecxhub_employee_payroll_managemenet
```

### Step 5: Set Up Database

```bash
mysql -u root -p < database_schema.sql
```

### Step 6: Configure Java Project

1. Edit `util/DBConnection.java`:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/payroll_db";
   private static final String USER = "root";
   private static final String PASSWORD = "your_password_here";
   ```

2. Create a compile script `compile.sh`:
   ```bash
   #!/bin/bash
   MYSQL_JAR="~/mysql-connector-java-8.0.x/mysql-connector-java-8.0.x.jar"
   javac -cp $MYSQL_JAR -d . util/DBConnection.java model/Employee.java model/Payslip.java dao/EmployeeDAO.java dao/PayrollDAO.java dao/Payroll.java service/PayrollService.java PayrollManagementSystem.java
   ```

3. Make it executable:
   ```bash
   chmod +x compile.sh
   ```

### Step 7: Compile Project

```bash
./compile.sh
```

### Step 8: Run Application

```bash
MYSQL_JAR="~/mysql-connector-java-8.0.x/mysql-connector-java-8.0.x.jar"
java -cp .:$MYSQL_JAR PayrollManagementSystem
```

---

## IDE Setup (IntelliJ IDEA)

### Step 1: Create Project
1. File → New → Project
2. Select "Java"
3. Choose Project SDK (Java 11 or higher)
4. Click "Create"

### Step 2: Add Project Files
1. Copy all files to project src/ directory
2. Create package structure:
   - model/
   - dao/
   - service/
   - util/

### Step 3: Add MySQL JDBC Library
1. File → Project Structure
2. Libraries → "+" → "Java"
3. Select mysql-connector-java JAR file
4. Click "Apply"

### Step 4: Configure Database
1. Edit util/DBConnection.java with your MySQL credentials

### Step 5: Run Project
1. Right-click PayrollManagementSystem.java
2. Select "Run PayrollManagementSystem.main()"

---

## IDE Setup (Eclipse)

### Step 1: Create Project
1. File → New → Java Project
2. Enter project name
3. Click "Finish"

### Step 2: Add Source Files
1. Copy all Java files to src/ folder
2. Organize in packages:
   - model/
   - dao/
   - service/
   - util/

### Step 3: Add MySQL JDBC Library
1. Right-click Project → Properties
2. Java Build Path → Libraries → Add External JARs
3. Select mysql-connector-java JAR

### Step 4: Configure Database
1. Edit util/DBConnection.java

### Step 5: Run Project
1. Right-click PayrollManagementSystem.java
2. Run As → Java Application

---

## IDE Setup (NetBeans)

### Step 1: Create Project
1. File → New Project
2. Select "Java Application"
3. Enter project name

### Step 2: Add Source Files
1. Right-click src/ folder
2. Create packages: model, dao, service, util
3. Add Java files to respective packages

### Step 3: Add MySQL JDBC Library
1. Right-click Project → Properties
2. Libraries → Add JAR/Folder
3. Select mysql-connector-java JAR

### Step 4: Configure Database
1. Edit util/DBConnection.java

### Step 5: Run Project
1. F6 or Run → Run Project

---

## Troubleshooting

### Issue: "MySQL Driver not found"
**Solution**:
- Ensure mysql-connector-java JAR is in classpath
- Check JAR file path is correct
- Add to CLASSPATH environment variable

### Issue: "Connection refused"
**Solution**:
```bash
# Check if MySQL is running
Windows: services.msc (look for MySQL service)
Linux: sudo systemctl status mysql
Mac: brew services list | grep mysql

# Start MySQL if not running
Windows: net start MySQL80
Linux: sudo systemctl start mysql
Mac: brew services start mysql
```

### Issue: "Access denied for user 'root'"
**Solution**:
- Verify password in DBConnection.java
- Reset MySQL password:
  ```bash
  mysql -u root
  ALTER USER 'root'@'localhost' IDENTIFIED BY 'new_password';
  FLUSH PRIVILEGES;
  EXIT;
  ```

### Issue: "Database payroll_db not found"
**Solution**:
```bash
mysql -u root -p < database_schema.sql
```

### Issue: "Table already exists"
**Solution**:
- Drop existing database:
  ```sql
  DROP DATABASE payroll_db;
  ```
- Then run schema script again

### Issue: "Permission denied" on Linux/Mac
**Solution**:
```bash
chmod +x compile.sh
chmod +x setup.sh
```

### Issue: "Out of Memory" error
**Solution**:
```bash
# Increase heap size
java -Xmx512m -Xms256m -cp .:$MYSQL_JAR PayrollManagementSystem
```

---

## Verification Steps

1. **Verify Java Installation**:
   ```bash
   java -version
   javac -version
   ```

2. **Verify MySQL Installation**:
   ```bash
   mysql --version
   mysql -u root -p -e "SELECT VERSION();"
   ```

3. **Verify Database Created**:
   ```bash
   mysql -u root -p -e "USE payroll_db; SHOW TABLES;"
   ```

4. **Verify JDBC Driver**:
   - Check JAR file exists at specified path
   - Check file size (should be ~2-3 MB)

5. **Verify Java Classpath**:
   ```bash
   echo $CLASSPATH  # Linux/Mac
   echo %CLASSPATH%  # Windows
   ```

---

## Performance Optimization

### Database Optimization
1. Create indexes on frequently searched columns:
   ```sql
   CREATE INDEX idx_emp_id ON employees(emp_id);
   CREATE INDEX idx_email ON employees(email);
   CREATE INDEX idx_payslip_emp ON payslips(emp_id);
   ```

2. Update statistics:
   ```sql
   ANALYZE TABLE employees;
   ANALYZE TABLE payslips;
   ```

### Java Optimization
1. Increase heap size for large datasets:
   ```bash
   java -Xmx1024m -Xms512m PayrollManagementSystem
   ```

2. Enable garbage collection optimization:
   ```bash
   java -XX:+UseG1GC PayrollManagementSystem
   ```

### Connection Pool (Optional Enhancement)
Consider using HikariCP for connection pooling in production:
```xml
<dependency>
    <groupId>com.zaxxer</groupId>
    <artifactId>HikariCP</artifactId>
    <version>5.0.1</version>
</dependency>
```

---

## Security Best Practices

1. **Change Default Password**:
   ```sql
   ALTER USER 'root'@'localhost' IDENTIFIED BY 'strong_password_here';
   ```

2. **Create Database User** (Don't use root in production):
   ```sql
   CREATE USER 'payroll_user'@'localhost' IDENTIFIED BY 'secure_password';
   GRANT ALL PRIVILEGES ON payroll_db.* TO 'payroll_user'@'localhost';
   FLUSH PRIVILEGES;
   ```

3. **Enable SSL for Database Connection** (Advanced):
   - Generate SSL certificates
   - Update connection string: `jdbc:mysql://localhost:3306/payroll_db?useSSL=true`

4. **Store Credentials Securely**:
   - Use environment variables instead of hardcoding
   - Use properties files with restricted permissions

5. **Regular Backups**:
   ```bash
   mysqldump -u root -p payroll_db > backup.sql
   ```

---

## Maintenance

### Regular Database Maintenance
```sql
-- Check table status
CHECK TABLE employees;
CHECK TABLE payslips;

-- Repair if needed
REPAIR TABLE employees;

-- Optimize tables
OPTIMIZE TABLE employees;
OPTIMIZE TABLE payslips;
```

### Backup Schedule
- Daily incremental backups
- Weekly full backups
- Store backups in separate location

### Log Management
- Monitor MySQL error log: `/var/log/mysql/error.log`
- Archive old logs regularly
- Monitor Java application logs
