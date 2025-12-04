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
        System.out.println("=== TESTER MENU ===");
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
        System.out.println("0) Logout");
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
                changePasswordFlow();              // From BaseMenu
                return true;

            case "2":
                listContactsWithSorting();        // AbstractContactMenu
                return true;

            case "3":
                showSearchMenu();      // AbstractContactMenu
                return true;


            case "0":
                System.out.println("Logging out...");
                return false;                     // The loop ends → Returns to LoginMenu

            default:
                System.out.println("Invalid option, try again.");
                return true;
        }
    }
}