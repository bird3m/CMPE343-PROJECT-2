public class TesterMenu extends AbstractContactMenu
{
    public TesterMenu(User currentUser)
    {
        super(currentUser);
    }

    @Override
    protected void printMenuHeader()
    {
        System.out.println("=== TESTER MENU ===");
    }

    @Override
    protected void printMenuOptions()
    {
        System.out.println("1) Change password");
        System.out.println("2) List all contacts (with sorting)");
        System.out.println("3) Search contacts by a single field");
        System.out.println("4) Search contacts by multiple fields (AND)");
        System.out.println("0) Logout");
    }

    @Override
    protected boolean handleChoice(String choice)
    {
        switch (choice)
        {
            case "1":
                changePasswordFlow();              // BaseMenu'den
                return true;

            case "2":
                listContactsWithSorting();        // AbstractContactMenu'den
                return true;

            case "3":
                searchContactsSingleField();      // AbstractContactMenu'den
                return true;

            case "4":
                searchContactsMultiField();       // AbstractContactMenu'den
                return true;

            case "0":
                System.out.println("Logging out...");
                return false;                     // döngü biter → LoginMenu'ya geri döner

            default:
                System.out.println("Invalid option, try again.");
                return true;
        }
    }
}
