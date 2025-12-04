import java.sql.Connection;
import java.sql.SQLException;

/**
 * Entry point of the Contact Management System.
 */
public class Main
{
    /**
     * Starts the application, plays intro animation, tests DB connection,
     * and opens the login menu.
     */
    public static void main(String[] args)
    {
        try {
            Animation.playIntro();
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        System.out.println("\nOpening login menu...\n");

        LoginMenu loginMenu = new LoginMenu();
        loginMenu.start();
    }
}
