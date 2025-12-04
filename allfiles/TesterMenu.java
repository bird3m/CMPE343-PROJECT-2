/**
 * Menu screen for Tester users.
 */
public class TesterMenu extends AbstractContactMenu
      
{
    /**
     * Creates a Tester menu for the given user.
     */
    public TesterMenu(User currentUser)
    {
        super(currentUser);
    }
    /**
     * Prints the Tester menu header.
     */
    @Override
    protected void printMenuHeader()
    {
        // Başlığı MAVİ və QALIN etdik
        System.out.println(BaseMenu.BLUE + BaseMenu.BOLD + "=== TESTER MENU ===" + BaseMenu.RESET);
    }
   /**
     * Prints available Tester menu options.
     */
    @Override
    protected void printMenuOptions()
    {
        System.out.println("1) Change password");
        System.out.println("2) List all contacts (with sorting)");
        System.out.println("3) Search contacts");
        // Logout seçimini QIRMIZI etdik
        System.out.println(BaseMenu.RED + "0) Logout" + BaseMenu.RESET);
    }
    /**
     * Handles the user's menu selection.
     */
    @Override
    protected boolean handleChoice(String choice)
    {
        switch (choice)
        {
            case "1":
                changePasswordFlow();              // From BaseMenu (artıq rənglidir)
                return true;

            case "2":
                listContactsWithSorting();        // AbstractContactMenu
                return true;

            case "3":
                showSearchMenu();      // AbstractContactMenu
                return true;


            case "0":
                System.out.println(BaseMenu.RED + "Logging out..." + BaseMenu.RESET);
                return false;                     // The loop ends → Returns to LoginMenu

            default:
                // Səhv seçimi QIRMIZI etdik
                System.out.println(BaseMenu.RED + "Invalid option, try again." + BaseMenu.RESET);
                return true;
        }
    }
}