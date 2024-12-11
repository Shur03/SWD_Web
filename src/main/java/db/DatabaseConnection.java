package db;

import java.sql.Connection;
import java.sql.DriverManager;

//Өгөгдлийн сантай холбогдох хэсгийг салгаж өгсөн. 
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/banktrans";
    private static final String USER = "shur";
    private static final String PASSWORD = "shur0369";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
}