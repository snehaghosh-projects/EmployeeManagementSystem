Employee Management System

Overview

The Employee Management System is a web-based application developed using Advanced Java technologies to streamline employee data management within an organization. The system provides separate interfaces for administrators and employees, enabling secure access to employee records, profile management, and payslip generation.

This project was developed as part of my learning and practical implementation of Java Web Development concepts including Servlets, JSP, JDBC, session management, email integration, and database connectivity.


Tech Stack

| Layer        | Technology                          |
|--------------|-------------------------------------|
| Frontend     | JSP, JSTL, HTML5, CSS3              |
| Backend      | Java Servlets (Jakarta EE 10)       |
| Database     | MySQL 8 via JDBC                    |
| Email        | JavaMail (Jakarta Mail 2)           |
| Build        | Maven 3                             |
| Server       | Apache Tomcat 11                    |



Project Structure

EmployeeMS/
├── database/
│   └── schema.sql                      
├── src/main/
│   ├── java/com/ems/
│   │   ├── dao/        EmployeeDAO, UserDAO, DepartmentDAO
│   │   ├── model/      Employee, User
│   │   ├── servlet/    LoginServlet, EmployeeServlet, ProfileServlet,
│   │   │               PayslipServlet, LogoutServlet
│   │   └── util/       DBConnection, EmailUtil
│   └── webapp/
│       ├── admin/      dashboard.jsp, employee-form.jsp, employee-view.jsp
│       ├── employee/   profile.jsp
│       ├── css/        style.css
│       ├── login.jsp
│       ├── payslip.jsp
│       ├── error.jsp
│       ├── index.jsp
│       └── WEB-INF/    web.xml
└── pom.xml


## Step 1 — Prerequisites
- Java JDK 11+  (`java -version`)
- Maven 3.8+    (`mvn -version`)
- MySQL 8        (running via MySQL Workbench)
- Apache Tomcat 11 (downloaded and extracted)
- VS Code + Extension Pack for Java + Tomcat for Java extension



## Step 2 — Database Setup
1. Open **MySQL Workbench**
2. Connect to `localhost:3306` with your root credentials
3. Open `database/schema.sql` and run it (Ctrl+Shift+Enter)
4. Verify: you should see `employee_management` database with tables

Update DB credentials in `src/main/java/com/ems/util/DBConnection.java`:



## Step 3 — Email Setup (JavaMail)
1. Go to your Google Account → Security → App Passwords
2. Generate a 16-character app password for "Mail"
3. Edit `src/main/java/com/ems/util/EmailUtil.java`:



## Step 4 — Build the WAR
cd EmployeeMS
mvn clean package

Output: `target/EmployeeMS.war`


## Step 5 — Deploy on Tomcat 11
cp target/EmployeeMS.war /path/to/tomcat/webapps/

# Start Tomcat
/path/to/tomcat/bin/startup.sh       # Linux/Mac
/path/to/tomcat/bin/startup.bat      # Windows
```
Visit: http://localhost:8080/EmployeeMS





## Step 6 — Test Logins
| Role     | Username  | Password  | Redirects to      |
|----------|-----------|-----------|-------------------|
| Admin    | admin     | admin123  | Admin Dashboard   |
| Employee | john.doe  | emp123    | Employee Profile  |


## Features Checklist
- [x] Role-based login (Admin / Employee)
- [x] Session management with timeout
- [x] Add / Edit / Delete / View employees (CRUD)
- [x] Pagination (10 per page, configurable)
- [x] Sorting by Name, Department, Salary, Designation
- [x] Search employees
- [x] Payslip generation with breakdown
- [x] Send payslip notification via email
- [x] Welcome email on new employee creation
- [x] Packaged as WAR for Tomcat deployment


