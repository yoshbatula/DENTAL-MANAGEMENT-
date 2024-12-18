package org.example.dentalmanagement.DATABASE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DATABASECONNECTIVITY {

    private static final String URL = "jdbc:mysql://localhost:3306/pystan_db";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public Connection getConnection() throws SQLException {
        try {
            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Connected to database");
            return con;
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }
}
