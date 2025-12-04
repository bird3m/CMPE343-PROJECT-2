import java.util.Scanner;

/**
 * Handles the login screen and redirects users to their role-specific menus.
 */
public class LoginMenu
{
    private final AuthService authService;
    private final Scanner scanner;

    public LoginMenu()
    {
        this.authService = new AuthService();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Starts the login loop and processes user choices.
     */
    public void start()
    {
        while (true)
        {
            System.out.println("\n=== LOGIN SCREEN ===");
            System.out.println("1) Login");
            System.out.println("9) Terminate Program");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();

            // ⭐ TERMINATE PROGRAM
            if (choice.equals("9"))
            {
                System.out.println("\nProgram terminated. Goodbye!");
                System.exit(0);
            }

            // ⭐ NORMAL LOGIN
            if (!choice.equals("1"))
            {
                System.out.println("Invalid option.\n");
                continue;
            }

            System.out.print("Username: ");
            String username = scanner.nextLine().trim();

            System.out.print("Password: ");
            String password = scanner.nextLine().trim();

            User user = authService.login(username, password);

            if (user != null)
            {
                System.out.println("\nLogin successful.\n");

                // ROLE MENU
                BaseMenu menu = createMenuForRole(user);
                menu.start();

                System.out.println("\nReturning to login screen...");
            }
            else
            {
                System.out.println("Invalid username or password.\n");
            }
        }
    }

    /**
     * Creates and returns the correct menu based on user role.
     */
    private BaseMenu createMenuForRole(User user)
    {
        switch (user.getRole())
        {
            case TESTER:
                return new TesterMenu(user);

            case JUNIOR_DEV:
                return new JuniorDeveloperMenu(user);

            case SENIOR_DEV:
                return new SeniorDeveloperMenu(user);

            case MANAGER:
                return new ManagerMenu(user);

            default:
                throw new IllegalStateException("Unknown role: " + user.getRole());
        }
    }
}