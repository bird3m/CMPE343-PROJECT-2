import java.util.Scanner;

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

        clearScreen(); //clears after login success

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

        clearScreen();
    }

    /**
     * Helper method used to get input from the user.
     *
     * @param prompt text shown to the user
     * @return trimmed input string
     * Helper method to get trimmed user input.

     */
    protected String getInputWithPrompt(String prompt)
    {
        System.out.print(prompt);
        String input = this.scanner.nextLine();
        return input.trim();
    }

    protected abstract void printMenuHeader();
    protected abstract void printMenuOptions();

    /**
     * @return true to continue menu loop, false to logout
     */
    protected abstract boolean handleChoice(String choice);

    /**
     * Password change flow shared by all menus.
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

    /**
     * Clears the terminal using ANSI escape codes.
     */
    protected void clearScreen()
    {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    protected String nullToEmpty(String s)
    {   
        return (s == null) ? "" : s;
    }

    protected String fit(String s, int width)
{
    if (s == null)
    {
        return "";
    }
    if (s.length() <= width)
    {
        return s;
    }
    // width en az 2 olmalı; bizde zaten daha büyük
    return s.substring(0, width - 1) + "…";
}

}
