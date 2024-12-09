package org.example.dentalmanagement.DATABASE;

import java.sql.SQLException;

public class database {

    public static void main(String[] args) throws SQLException {

        DATABASECONNECTIVITY db = new DATABASECONNECTIVITY();

        System.out.println(db.getConnection());
    }
}
