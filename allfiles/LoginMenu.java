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
            // BAŞLIĞI MAVİ VƏ QALIN etdik
            System.out.println(BaseMenu.BLUE + BaseMenu.BOLD + "\n=== LOGIN SCREEN ===" + BaseMenu.RESET);
            System.out.println("1) Login");
            System.out.println(BaseMenu.RED + "9) Terminate Program" + BaseMenu.RESET); // ÇIXIŞI QIRMIZI
            System.out.print(BaseMenu.CYAN + "Choice: " + BaseMenu.RESET); // Seçim prompt-u GÖY

            String choice = scanner.nextLine().trim();

            // TERMINATE PROGRAM
            if (choice.equals("9"))
            {
                System.out.println("\nProgram terminated. Goodbye!");
                System.exit(0);
            }

            //NORMAL LOGIN
            if (!choice.equals("1"))
            {
                // Səhv mesajı QIRMIZI rəngdə
                System.out.println(BaseMenu.RED + "Invalid option.\n" + BaseMenu.RESET);
                continue;
            }

            System.out.print("Username: ");
            String username = scanner.nextLine().trim();

            System.out.print("Password: ");
            String password = scanner.nextLine().trim();

            User user = authService.login(username, password);

            if (user != null)
            {
                // Uğur mesajı YAŞIL rəngdə
                System.out.println(BaseMenu.GREEN + "\nLogin successful." + BaseMenu.RESET + "\n");

                // ROLE MENU
                BaseMenu menu = createMenuForRole(user);
                menu.start();

                System.out.println("\nReturning to login screen...");
            }
            else
            {
                // Səhv mesajı QIRMIZI rəngdə
                System.out.println(BaseMenu.RED + "Invalid username or password.\n" + BaseMenu.RESET);
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