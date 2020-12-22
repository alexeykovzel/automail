package com.alexeykovzel;

import java.sql.*;

class DBWorker {
    static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/maindb?useSSL=false";
    static final String DB_USER = "alexey";
    static final String DB_PASSWORD = "admin134679";
    static final String DB_DRIVER = "com.mysql.jdbc.Driver";

    static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }
}
