package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Database connection details
    private static final String URL = "jdbc:mysql://localhost:3306/cmpe343_contacts?serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "myuser"; 
    private static final String PASSWORD = "1234";

    /**
     * Opens a new connection to the MySQL database.
     * @return A new Connection object.
     * @throws SQLException If connection fails.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ ERROR: MySQL JDBC Driver not found. Ensure the JAR file is in 'Referenced Libraries'.");
            throw new SQLException("JDBC Driver Not Found", e);
        }
        
        // Establish and return the connection
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}