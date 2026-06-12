package com.ems.dao;

import com.ems.model.User;
import com.ems.util.DBConnection;

import java.sql.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserDAO {

    /** Validates credentials; returns the matching User or null. */
    public User authenticate(String username, String password) {
        String hashed = md5(password);
        String sql = "SELECT * FROM users WHERE username=? AND password=? AND is_active=1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, hashed);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User u = new User();
                u.setUserId(rs.getInt("user_id"));
                u.setUsername(rs.getString("username"));
                u.setRole(rs.getString("role"));
                u.setActive(rs.getInt("is_active") == 1);
                return u;
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    /** Creates a new user account; returns the generated user_id or -1. */
    public int createUser(String username, String plainPassword, String role) {
        String sql = "INSERT INTO users (username, password, role) VALUES (?,?,?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql,
                 Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, username);
            ps.setString(2, md5(plainPassword));
            ps.setString(3, role);
            ps.executeUpdate();
            ResultSet keys = ps.getGeneratedKeys();
            if (keys.next()) return keys.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return -1;
    }

    /** MD5 hash helper (matches the schema seed data). */
    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
