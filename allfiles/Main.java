import java.sql.Connection;
import java.sql.SQLException;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Starting Contact Management System...");

        try (Connection conn = DatabaseConnection.getConnection())
        {
            if (conn == null)
            {
                System.out.println("Database connection failed (null returned). Please check MySQL settings.");
                return;
            }

            System.out.println("Database connection successful.");
        }
        catch (SQLException e)
        {
            System.out.println("Error while closing test connection: " + e.getMessage());
        }

        System.out.println("Opening login menu...\n");

        LoginMenu loginMenu = new LoginMenu();
        loginMenu.start();

    }
}
