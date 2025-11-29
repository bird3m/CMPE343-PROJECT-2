import java.util.Scanner;

public abstract class BaseMenu
{
    protected final User currentUser;
    protected final Scanner scanner;
    protected final AuthService authService;

    protected BaseMenu(User currentUser)
    {
        this.currentUser = currentUser;
        this.scanner = new Scanner(System.in);
        this.authService = new AuthService();
    }

    public final void start()
    {
        boolean running = true;

        while (running)
        {
            System.out.println();
            System.out.println("Logged in as: " + currentUser.getUsername()
                + " (" + currentUser.getRole() + ")");

            printMenuHeader();
            printMenuOptions();

            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            running = handleChoice(choice);
        }
    }

    // Her rolde üstte bir başlık için
    protected abstract void printMenuHeader();

    // Her rolde menü seçenekleri için
    protected abstract void printMenuOptions();

    /**
     * @param choice kullanıcının girdiği seçim
     * @return true → menü dönmeye devam etsin
     *         false → logout (LoginMenu'ya dön)
     */
    protected abstract boolean handleChoice(String choice);

    // Tüm rollerin ortak change password akışı
    protected void changePasswordFlow()
    {
        System.out.println("\n-- Change Password --");

        System.out.print("Old password: ");
        String oldPass = scanner.nextLine().trim();

        System.out.print("New password: ");
        String newPass1 = scanner.nextLine().trim();

        System.out.print("Repeat new password: ");
        String newPass2 = scanner.nextLine().trim();

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
