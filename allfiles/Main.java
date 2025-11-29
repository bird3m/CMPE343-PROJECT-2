import java.sql.Connection;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("Starting Contact Management System...");

        Connection conn = DatabaseConnection.getConnection();

        if (conn == null)
        {
            System.out.println("Database connection failed (null returned). Please check MySQL settings.");
            return;
        }

        System.out.println("Database connection successful.");
        System.out.println("Opening login menu...\n");

        LoginMenu loginMenu = new LoginMenu();
        loginMenu.start();

        System.out.println("Program terminated. Goodbye.");
    }
}
