import java.util.Scanner;

/**
 * Base class for all menus. Stores the logged-in user, input scanner,
 * authentication service and user DAO. Shared across all role menus.
 */
public abstract class BaseMenu
{
    // -------------------------------------------------------------------
    // Rəng Sabitləri (ANSI Escape Kodları) - Bütün siniflər üçün istifadə olunur
    // -------------------------------------------------------------------
    public static final String RESET = "\033[0m";     // Normal rəngə qayıtmaq üçün
    public static final String RED = "\033[0;31m";       // Qırmızı (Səhvlər/Xəbərdarlıqlar)
    public static final String GREEN = "\033[0;32m";     // Yaşıl (Uğur/Müsbət)
    public static final String YELLOW = "\033[0;33m";    // Sarı
    public static final String BLUE = "\033[0;34m";      // Mavi (İstifadəçi adı/Başlıqlar)
    public static final String CYAN = "\033[0;36m";      // Göy (İnformasiya/Promptlar)
    public static final String BOLD = "\033[1m";   
          // Qalın şrift
    // -------------------------------------------------------------------

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
            
            // İstifadəçi məlumatı MAVİ rəngdə
            System.out.print(BaseMenu.BLUE);
            System.out.println("Logged in as: " + currentUser.getName() + " " + currentUser.getSurname()
                + " (" + currentUser.getRole() + ")" + BaseMenu.RESET);

            printMenuHeader();
            printMenuOptions();

            // Seçim prompt-u GÖY rəngdə
            System.out.print(BaseMenu.CYAN + "Choice: " + BaseMenu.RESET); 
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
        clearScreen();
        System.out.println(BaseMenu.BOLD + "\n-- Change Password --" + BaseMenu.RESET); 

        System.out.print("Current password: ");
        String oldPw = scanner.nextLine().trim();

        System.out.print("New password: ");
        String newPw1 = scanner.nextLine().trim();

        System.out.print("Repeat new password: ");
        String newPw2 = scanner.nextLine().trim();

        boolean ok = authService.changePassword(currentUser, oldPw, newPw1, newPw2);

        if (ok)
        {
            // Uğur mesajı YAŞIL
            System.out.println(BaseMenu.GREEN + "Password updated successfully." + BaseMenu.RESET);
        }
        else
        {
            // Səhv mesajı QIRMIZI
            System.out.println(BaseMenu.RED + "Password NOT changed (see error message above)." + BaseMenu.RESET);
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
    // width should be at least 2
    return s.substring(0, width - 1) + "…";
}

}
