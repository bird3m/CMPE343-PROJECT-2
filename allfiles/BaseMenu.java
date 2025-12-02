import java.util.Scanner;
import java.time.LocalDateTime;

public abstract class BaseMenu
{
    protected final User currentUser;
    protected final Scanner scanner;
    protected final AuthService authService;
    // UserDao'yu burada tanımlamak, changePasswordFlow'u desteklemek için en kolay yoldur.
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
     * Kullanıcıdan girdi almak için kullanılan yardımcı metot.
     * @param prompt Kullanıcıya gösterilecek istem metni.
     * @return Temizlenmiş girdi dizisi.
     */
    protected String getInputWithPrompt(String prompt)
    {
        System.out.print(prompt);
        String input = this.scanner.nextLine();
        return input.trim();
    }
    
    // Her rolde üstte bir başlık için
    protected abstract void printMenuHeader();

    // Her rolde menü seçenekleri için
    protected abstract void printMenuOptions();

    /**
     * @param choice kullanıcının girdiği seçim
     * @return true → menü dönmeye devam etsin
     * false → logout (LoginMenu'ya dön)
     */
    protected abstract boolean handleChoice(String choice);

// Tüm rollerin ortak change password akışı
protected void changePasswordFlow()
{
    System.out.println("\n-- Change Password --");

    System.out.print("Current password: ");
    String oldPw = scanner.nextLine().trim();

    System.out.print("New password: ");
    String newPw1 = scanner.nextLine().trim();

    System.out.print("Repeat new password: ");
    String newPw2 = scanner.nextLine().trim();

    // AuthService: User + 3 String bekliyor
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


}