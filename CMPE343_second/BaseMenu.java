import java.util.Scanner;
import java.time.LocalDateTime;

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
     * Helper method used for safely receiving input from the user.
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
    
    /** Header shown at the top of each role’s menu. */
    protected abstract void printMenuHeader();

    /** Menu options specific to each role. */
    protected abstract void printMenuOptions();

    /**
     * Handles user's menu selection.
     *
     * @param choice user’s input option
     * @return true → continue menu loop  
     *         false → logout and return to LoginMenu
     */
    protected abstract boolean handleChoice(String choice);

    /**
     * Common change-password flow shared by all roles.
     */
    protected void changePasswordFlow()
    {
        System.out.println("\n-- Change Password --");

        String oldPass = getInputWithPrompt("Old password: ");
        String newPass1 = getInputWithPrompt("New password: ");
        String newPass2 = getInputWithPrompt("Repeat new password: ");

        /**
         * We assume AuthService performs necessary hashing/salt operations
         * and uses UserDao internally.  
         * It's important that AuthService.changePassword updates the User object.
         */
        boolean ok = authService.changePassword(currentUser, oldPass, newPass1, newPass2);

        if (ok)
        {
            System.out.println("Password changed successfully.");
        }
        else
        {
            System.out.println("Password change failed.");
        }
    }
}
