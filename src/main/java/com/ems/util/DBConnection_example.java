package com.ems.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnection_example {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL    =
        "jdbc:mysql://localhost:3306/employee_management?useSSL=false&serverTimezone=UTC";
    private static final String USER   = "root";        // ← your MySQL username
    private static final String PASS   = "";        // ← your MySQL password

    static {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL JDBC Driver not found.", e);
        }
    }

    /** Returns a new connection from the driver manager. */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
        
    }

    /** Safely closes a connection (null-safe). */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try { conn.close(); } catch (SQLException ignored) {}
        }
    }
}

