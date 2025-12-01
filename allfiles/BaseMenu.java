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

        String oldPass = getInputWithPrompt("Old password: ");
        String newPass1 = getInputWithPrompt("New password: ");
        String newPass2 = getInputWithPrompt("Repeat new password: ");

        // AuthService'in gerekli hash ve salt işlemlerini yapıp UserDao'yu çağırdığını varsayıyoruz.
        // AuthService.changePassword metodunuzun User nesnesini güncellemesi önemlidir.
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