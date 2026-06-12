package com.ems.dao;

import com.ems.util.DBConnection;

import java.sql.*;
import java.util.*;

public class DepartmentDAO {

    /** Returns all departments as a map of deptId → deptName. */
    public Map<Integer, String> getAllDepartments() {
        Map<Integer, String> map = new LinkedHashMap<>();
        String sql = "SELECT dept_id, dept_name FROM departments ORDER BY dept_name";
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next())
                map.put(rs.getInt("dept_id"), rs.getString("dept_name"));
        } catch (SQLException e) { e.printStackTrace(); }
        return map;
    }
}
