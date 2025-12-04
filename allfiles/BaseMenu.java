import java.util.Scanner;
import java.time.LocalDateTime;

/**
 * Base class for all menus. Stores the logged-in user, input scanner,
 * authentication service and user DAO. Shared across all role menus.
 */
public abstract class BaseMenu
{
    protected final User currentUser;
    protected final Scanner scanner;
    protected final AuthService authService;

    /**
     * Defining UserDao here is the simplest way to support changePasswordFlow.
     */
    protected final UserDao userDao; 

    protected BaseMenu(User currentUser)
    {
        this.currentUser = currentUser;
        this.scanner = new Scanner(System.in);
        this.authService = new AuthService();
        this.userDao = new UserDao();
    }

    public final void start()
    {
        boolean running = true;

        while (running)
        {
            System.out.println();
            System.out.println("Logged in as: " + currentUser.getName() + " " + currentUser.getSurname()
                + " (" + currentUser.getRole() + ")");

            printMenuHeader();
            printMenuOptions();

            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            running = handleChoice(choice);
        }
    }

    /**
     * Helper method used to get input from the user.
     *
     * @param prompt text shown to the user
     * @return trimmed input string
     */
    protected String getInputWithPrompt(String prompt)
    {
        System.out.print(prompt);
        String input = this.scanner.nextLine();
        return input.trim();
    }
    
    // Header for each role menu
    protected abstract void printMenuHeader();

    // Menu options for each role
    protected abstract void printMenuOptions();

    /**
     * @param choice user’s menu selection
     * @return true to continue the menu loop, false to logout
     */
    protected abstract boolean handleChoice(String choice);

    /**
     * Shared password change flow used by all roles.
     */
    protected void changePasswordFlow()
    {
        System.out.println("\n-- Change Password --");

        System.out.print("Current password: ");
        String oldPw = scanner.nextLine().trim();

        System.out.print("New password: ");
        String newPw1 = scanner.nextLine().trim();

        System.out.print("Repeat new password: ");
        String newPw2 = scanner.nextLine().trim();

        boolean ok = authService.changePassword(currentUser, oldPw, newPw1, newPw2);

        if (ok)
        {
            System.out.println("Password updated successfully.");
        }
        else
        {
            System.out.println("Password NOT changed (see error message above).");
        }
    }

     protected void clearScreen()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}