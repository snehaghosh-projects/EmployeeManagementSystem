
package com.ems.util;

import java.sql.Connection;

public class TestDB {
    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnection();

            if (conn != null) {
                System.out.println("Database connected successfully!");
                System.out.println("URL: " + conn.getMetaData().getURL());
            }

            conn.close();
        } catch (Exception e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
}
