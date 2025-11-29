import java.util.Scanner;

public class LoginMenu
{
    private final AuthService authService;
    private final Scanner scanner;

    public LoginMenu()
    {
        this.authService = new AuthService();
        this.scanner = new Scanner(System.in);
    }

    public void start()
    {
        while (true)
        {
            System.out.println("\n=== LOGIN SCREEN ===");

            System.out.print("Username: ");
            String username = scanner.nextLine().trim();

            System.out.print("Password: ");
            String password = scanner.nextLine().trim();

            User user = authService.login(username, password);

            if (user != null)
            {
                System.out.println("\nLogin successful.");
                System.out.println("Welcome, " + user.getName() + " " + user.getSurname()
                    + " (" + user.getRole() + ")\n");

                openMenuForRole(user);

                System.out.println("\nReturning to login screen...");
            }
            else
            {
                System.out.println("Invalid login. Try again.\n");
            }
        }
    }

    private void openMenuForRole(User user)
    {
        switch (user.getRole())
        {
            case TESTER:
                new TesterMenu(user).start();
                break;

            case JUNIOR_DEV:
                new JuniorDeveloperMenu(user).start();
                break;

            case SENIOR_DEV:
                new SeniorDeveloperMenu(user).start();
                break;

            case MANAGER:
                new ManagerMenu(user).start();
                break;

            default:
                System.out.println("Unknown role.");
        }

    }
}
