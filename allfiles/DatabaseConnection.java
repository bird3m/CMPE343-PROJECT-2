import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Provides a connection to the MySQL database using JDBC.
 * Returns a Connection object or null if the connection fails.
 */
public class DatabaseConnection
{
    private static final String URL =
    "jdbc:mysql://localhost:3306/cmpe343_contacts"
    + "?useSSL=false"
    + "&allowPublicKeyRetrieval=true"
    + "&serverTimezone=UTC";

    private static final String USERNAME = "myuser";
    private static final String PASSWORD = "1234";

    /**
     * Loads the JDBC driver and establishes a MySQL database connection.
     *
     * @return a Connection object if successful, otherwise null
     */
    public static Connection getConnection() 
    {
        try
        {
            // Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("JDBC Driver not found. Please install MySQL Connector/J.");
            System.out.println("Error: " + e.getMessage());
            return null;
        }
        catch (SQLException e)
        {
            System.out.println("SQL error during connection: " + e.getMessage());
            return null;
        }
    }
}