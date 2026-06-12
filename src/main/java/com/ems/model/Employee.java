package com.ems.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Employee {

    private int       empId;
    private int       userId;
    private String    firstName;
    private String    lastName;
    private String    email;
    private String    phone;
    private int       deptId;
    private String    deptName;    // joined from departments
    private String    designation;
    private BigDecimal salary;
    private Date      hireDate;
    private String    address;
    private String    createdAt;
    private String    updatedAt;

    // ── Constructors ──────────────────────────────────────────
    public Employee() {}

    public Employee(String firstName, String lastName, String email,
                    String phone, int deptId, String designation,
                    BigDecimal salary, Date hireDate, String address) {
        this.firstName   = firstName;
        this.lastName    = lastName;
        this.email       = email;
        this.phone       = phone;
        this.deptId      = deptId;
        this.designation = designation;
        this.salary      = salary;
        this.hireDate    = hireDate;
        this.address     = address;
    }

    // ── Getters & Setters ─────────────────────────────────────
    public int       getEmpId()       { return empId; }
    public void      setEmpId(int v)  { this.empId = v; }

    public int       getUserId()      { return userId; }
    public void      setUserId(int v) { this.userId = v; }

    public String    getFirstName()        { return firstName; }
    public void      setFirstName(String v){ this.firstName = v; }

    public String    getLastName()         { return lastName; }
    public void      setLastName(String v) { this.lastName = v; }

    public String    getFullName()         { return firstName + " " + lastName; }

    public String    getEmail()       { return email; }
    public void      setEmail(String v){ this.email = v; }

    public String    getPhone()       { return phone; }
    public void      setPhone(String v){ this.phone = v; }

    public int       getDeptId()      { return deptId; }
    public void      setDeptId(int v) { this.deptId = v; }

    public String    getDeptName()        { return deptName; }
    public void      setDeptName(String v){ this.deptName = v; }

    public String    getDesignation()        { return designation; }
    public void      setDesignation(String v){ this.designation = v; }

    public BigDecimal getSalary()          { return salary; }
    public void       setSalary(BigDecimal v){ this.salary = v; }

    public Date      getHireDate()         { return hireDate; }
    public void      setHireDate(Date v)   { this.hireDate = v; }

    public String    getAddress()          { return address; }
    public void      setAddress(String v)  { this.address = v; }

    public String    getCreatedAt()        { return createdAt; }
    public void      setCreatedAt(String v){ this.createdAt = v; }

    public String    getUpdatedAt()        { return updatedAt; }
    public void      setUpdatedAt(String v){ this.updatedAt = v; }
}
