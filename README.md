# Employee Management System

## Overview

The Employee Management System is a web-based application developed using Advanced Java technologies to streamline employee data management within an organization. The system provides separate interfaces for administrators and employees, enabling secure access to employee records, profile management, announcements, and email notifications.

This project was developed to gain practical experience in Java Web Development concepts, including Servlets, JSP, JDBC, session management, authentication, email integration, and cloud deployment.

---

## Features

### Authentication & Security

* Role-based login (Admin / Employee)
* Secure password hashing using SHA-256
* First-time login password change enforcement
* Session management with timeout handling
* Protected role-based access control

### Employee Management

* Add new employees
* View employee records
* Update employee information
* Delete employee records
* Search employees
* Employee profile management

### Employee Features

* View personal profile
* Change password
* Upload profile picture
* Remove profile picture
* Dark mode support

### Announcement System

Administrators can send announcements to:

* All employees
* Employees from the same department
* Custom employee groups
* Selected employee ranges

### Email Notifications

Automatic email notifications for:

* Employee account creation
* Temporary password generation
* Employee information updates
* Employee account deletion

### Additional Features

* Pagination support
* Sorting by employee attributes
* Responsive user interface
* Maven-based project structure
* Cloud database integration

---

## Tech Stack

| Layer               | Technology                         |
| ------------------- | ---------------------------------- |
| Frontend            | JSP, JSTL, HTML5, CSS3, JavaScript |
| Backend             | Java Servlets (Jakarta EE)         |
| Database            | MySQL 8, JDBC                      |
| Email Service       | Brevo Email API                    |
| Build Tool          | Maven                              |
| Application Server  | Apache Tomcat                      |
| Cloud Database      | Aiven MySQL                        |
| Deployment Platform | Render                             |

---

## Project Structure

```text
EmployeeMS/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   ├── resources/
│   │   └── webapp/
│   │
│   └── test/
│
├── pom.xml
├── README.md
└── target/
```

---

## Prerequisites

Before running the project, ensure the following are installed:

* Java JDK 11 or later
* Maven 3.8+
* MySQL 8+
* Apache Tomcat
* VS Code (or IntelliJ IDEA/Eclipse)

Verify installation:

```bash
java -version
mvn -version
```

---

## Database Setup

1. Open MySQL Workbench.
2. Create a database named:

```sql
CREATE DATABASE employee_management;
```

3. Import or execute the required SQL schema.
4. Update database credentials in the application's database configuration file.

Example:

```java
jdbc:mysql://localhost:3306/employee_management
```

---

## Build the Project

Clone the repository and build using Maven:

```bash
git clone <repository-url>
cd EmployeeMS
mvn clean package
```

Generated WAR file:

```text
target/EmployeeMS.war
```

---

## Deployment

### Deploy on Apache Tomcat

Copy the generated WAR file into Tomcat's `webapps` directory:

```text
tomcat/webapps/
```

Start Tomcat:

**Windows**

```bash
startup.bat
```

**Linux / macOS**

```bash
./startup.sh
```

Access the application:

```text
http://localhost:8080/EmployeeMS
```

---

## Cloud Deployment

The application is deployed using:

* Render (Application Hosting)
* Aiven MySQL (Managed Database)

Deployment workflow:

1. Build Maven WAR package
2. Push code to GitHub
3. Connect repository to Render
4. Configure environment variables
5. Connect Aiven MySQL database
6. Deploy application

---

## Learning Outcomes

Through this project, I gained hands-on experience in:

* Java Web Application Development
* JSP and Servlet Architecture
* JDBC Database Connectivity
* Authentication & Authorization
* Session Management
* Email API Integration
* Maven Build Automation
* Cloud Deployment
* MVC Design Pattern

---

## Future Enhancements

* Attendance Management System
* Leave Management Module
* Payroll Management
* Employee Performance Tracking
* Dashboard Analytics
* Two-Factor Authentication (2FA)

---

## Author

**Sneha Ghosh**

Bachelor of Computer Applications (BCA)

Advanced Java Project
