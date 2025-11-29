import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ManagerMenu extends BaseMenu
{
    private final ContactDao contactDao;
    private final UserDao userDao;

    public ManagerMenu(User currentUser)
    {
        super(currentUser);
        this.contactDao = new ContactDao();
        this.userDao = new UserDao();
    }

    @Override
    protected void printMenuHeader()
    {
        System.out.println("=== MANAGER MENU ===");
    }

    @Override
    protected void printMenuOptions()
    {
        System.out.println("1) Change password");
        System.out.println("2) Show contacts statistical info");
        System.out.println("3) List all users (TODO)");
        System.out.println("4) Add/employ new user (TODO)");
        System.out.println("5) Update existing user (TODO)");
        System.out.println("6) Delete/fire existing user (TODO)");
        System.out.println("0) Logout");
    }

    @Override
    protected boolean handleChoice(String choice)
    {
        switch (choice)
        {
            case "1":
                changePasswordFlow();
                return true;

            case "2":
                showContactStatistics();
                return true;

            case "3":
                System.out.println("List all users: not implemented yet (TODO).");
                return true;

            case "4":
                System.out.println("Add new user: not implemented yet (TODO).");
                return true;

            case "5":
                System.out.println("Update user: not implemented yet (TODO).");
                return true;

            case "6":
                System.out.println("Delete user: not implemented yet (TODO).");
                return true;

            case "0":
                System.out.println("Logging out...");
                return false;

            default:
                System.out.println("Invalid option, try again.");
                return true;
        }
    }

    private void showContactStatistics()
    {
        System.out.println("\n-- Contact Statistics --");

        List<Contact> all = contactDao.getAllContacts("contact_id", true);
        int total = all.size();
        System.out.println("Total number of contacts: " + total);

        Map<String, Integer> byLastName = new HashMap<>();
        Map<String, Integer> byEmailDomain = new HashMap<>();

        LocalDate minBirth = null;
        LocalDate maxBirth = null;

        for (Contact c : all)
        {
            // last_name'lere göre dağılım
            String ln = c.getLastName();
            if (ln != null)
            {
                byLastName.put(ln, byLastName.getOrDefault(ln, 0) + 1);
            }

            // email domain'e göre
            String email = c.getEmail();
            if (email != null)
            {
                int at = email.indexOf('@');
                if (at >= 0 && at < email.length() - 1)
                {
                    String domain = email.substring(at + 1).toLowerCase();
                    byEmailDomain.put(domain, byEmailDomain.getOrDefault(domain, 0) + 1);
                }
            }

            // doğum tarihi aralığı
            if (c.getBirthDate() != null)
            {
                LocalDate bd = c.getBirthDate();
                if (minBirth == null || bd.isBefore(minBirth))
                {
                    minBirth = bd;
                }
                if (maxBirth == null || bd.isAfter(maxBirth))
                {
                    maxBirth = bd;
                }
            }
        }

        System.out.println("\nContacts per last name:");
        for (Map.Entry<String, Integer> e : byLastName.entrySet())
        {
            System.out.println("  " + e.getKey() + ": " + e.getValue());
        }

        System.out.println("\nContacts per email domain:");
        for (Map.Entry<String, Integer> e : byEmailDomain.entrySet())
        {
            System.out.println("  " + e.getKey() + ": " + e.getValue());
        }

        System.out.println("\nBirth date range:");
        if (minBirth != null && maxBirth != null)
        {
            System.out.println("  Earliest birth date: " + minBirth);
            System.out.println("  Latest   birth date: " + maxBirth);
        }
        else
        {
            System.out.println("  No birth date information available.");
        }
    }
}
