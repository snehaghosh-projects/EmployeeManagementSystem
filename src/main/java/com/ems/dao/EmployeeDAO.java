package com.ems.dao;

import com.ems.model.Employee;
import com.ems.util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    // ── SELECT ALL (paginated, with dept join, sortable) ──────
    public List<Employee> getAllEmployees(int page, int pageSize,
                                         String sortCol, String sortDir) {
        List<Employee> list = new ArrayList<>();
        // Whitelist to prevent SQL injection via sort parameters
        String colKey = sortCol == null ? "" : sortCol;
        String col;
        switch (colKey) {
            case "name":
                col = "e.first_name";
                break;
            case "department":
                col = "d.dept_name";
                break;
            case "salary":
                col = "e.salary";
                break;
            case "designation":
                col = "e.designation";
                break;
            default:
                col = "e.emp_id";
        }
        String dir = "desc".equalsIgnoreCase(sortDir) ? "DESC" : "ASC";
        String sql = "SELECT e.*, d.dept_name "
            + "FROM employees e "
            + "LEFT JOIN departments d ON e.dept_id = d.dept_id "
            + "ORDER BY " + col + " " + dir + " "
            + "LIMIT ? OFFSET ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, pageSize);
            ps.setInt(2, (page - 1) * pageSize);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /** Total employee count (for pagination). */
    public int getTotalCount() {
        String sql = "SELECT COUNT(*) FROM employees";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    // ── SELECT BY ID ─────────────────────────────────────────
    public Employee getEmployeeById(int empId) {
        String sql = "SELECT e.*, d.dept_name "
            + "FROM employees e "
            + "LEFT JOIN departments d ON e.dept_id = d.dept_id "
            + "WHERE e.emp_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    /** Fetch an employee by their linked user_id (for self-service view). */
    public Employee getEmployeeByUserId(int userId) {
        String sql = "SELECT e.*, d.dept_name "
            + "FROM employees e "
            + "LEFT JOIN departments d ON e.dept_id = d.dept_id "
            + "WHERE e.user_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    // ── INSERT ────────────────────────────────────────────────
    public boolean addEmployee(Employee emp) {
        String sql = "INSERT INTO employees "
            + "(user_id, first_name, last_name, email, phone, "
            + "dept_id, designation, salary, hire_date, address) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, emp.getUserId());
            ps.setString(2, emp.getFirstName());
            ps.setString(3, emp.getLastName());
            ps.setString(4, emp.getEmail());
            ps.setString(5, emp.getPhone());
            ps.setInt(6, emp.getDeptId());
            ps.setString(7, emp.getDesignation());
            ps.setBigDecimal(8, emp.getSalary());
            ps.setDate(9, emp.getHireDate());
            ps.setString(10, emp.getAddress());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ── UPDATE ────────────────────────────────────────────────
    public boolean updateEmployee(Employee emp) {
        String sql = "UPDATE employees "
            + "SET first_name=?, last_name=?, email=?, phone=?, "
            + "dept_id=?, designation=?, salary=?, hire_date=?, "
            + "address=?, updated_at=NOW() "
            + "WHERE emp_id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, emp.getFirstName());
            ps.setString(2, emp.getLastName());
            ps.setString(3, emp.getEmail());
            ps.setString(4, emp.getPhone());
            ps.setInt(5, emp.getDeptId());
            ps.setString(6, emp.getDesignation());
            ps.setBigDecimal(7, emp.getSalary());
            ps.setDate(8, emp.getHireDate());
            ps.setString(9, emp.getAddress());
            ps.setInt(10, emp.getEmpId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ── DELETE ────────────────────────────────────────────────
    public boolean deleteEmployee(int empId) {
        String sql = "DELETE FROM employees WHERE emp_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ── SEARCH ───────────────────────────────────────────────
    public List<Employee> searchEmployees(String keyword) {
        List<Employee> list = new ArrayList<>();
        String sql = "SELECT e.*, d.dept_name "
            + "FROM employees e "
            + "LEFT JOIN departments d ON e.dept_id = d.dept_id "
            + "WHERE e.first_name LIKE ? OR e.last_name LIKE ? "
            + "OR e.email LIKE ? OR d.dept_name LIKE ? "
            + "OR e.designation LIKE ? "
            + "ORDER BY e.first_name";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String kw = "%" + keyword + "%";
            for (int i = 1; i <= 5; i++) ps.setString(i, kw);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }

    // ── PRIVATE HELPER ────────────────────────────────────────
    private Employee mapRow(ResultSet rs) throws SQLException {
        Employee e = new Employee();
        e.setEmpId(rs.getInt("emp_id"));
        e.setUserId(rs.getInt("user_id"));
        e.setFirstName(rs.getString("first_name"));
        e.setLastName(rs.getString("last_name"));
        e.setEmail(rs.getString("email"));
        e.setPhone(rs.getString("phone"));
        e.setDeptId(rs.getInt("dept_id"));
        e.setDeptName(rs.getString("dept_name"));
        e.setDesignation(rs.getString("designation"));
        e.setSalary(rs.getBigDecimal("salary"));
        e.setHireDate(rs.getDate("hire_date"));
        e.setAddress(rs.getString("address"));
        // updated_at may not exist in all queries
        try { e.setUpdatedAt(rs.getString("updated_at")); } catch (SQLException ignored) {}
        return e;
    }
}
