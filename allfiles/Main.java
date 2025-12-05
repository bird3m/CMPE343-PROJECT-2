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

        
        System.out.println(BaseMenu.YELLOW + "Starting Contact Management System..." + BaseMenu.RESET);

        try (Connection conn = DatabaseConnection.getConnection())
        {
            if (conn == null)
            {
                System.out.println(BaseMenu.RED + "Database connection failed (null returned). Please check MySQL settings." + BaseMenu.RESET);
                return;
            }

            System.out.println(BaseMenu.GREEN + "Database connection successful." + BaseMenu.RESET);
        }
        catch (SQLException e)
        {
            
            System.out.println(BaseMenu.RED + "Error while closing test connection: " + e.getMessage() + BaseMenu.RESET);
        }

        
        System.out.println(BaseMenu.CYAN + "\nOpening login menu...\n" + BaseMenu.RESET);

        LoginMenu loginMenu = new LoginMenu();
        loginMenu.start();
    }
}