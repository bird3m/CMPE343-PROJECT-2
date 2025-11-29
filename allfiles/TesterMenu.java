import java.util.List;
import java.util.Scanner;

public class TesterMenu
{
    private final User currentUser;
    private final Scanner scanner;
    private final ContactDao contactDao;

    public TesterMenu(User currentUser)
    {
        this.currentUser = currentUser;
        this.scanner = new Scanner(System.in);
        this.contactDao = new ContactDao();
    }

    public void start()
    {
        boolean running = true;

        while (running)
        {
            System.out.println();
            System.out.println("=== TESTER MENU ===");
            System.out.println("Logged in as: " + currentUser.getUsername());
            System.out.println("1) Change Password (TODO)");
            System.out.println("2) List Contacts (with sorting)");
            System.out.println("3) Search Contacts (by one field)");
            System.out.println("0) Logout");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice)
            {
                case "1":
                    System.out.println("Change password TODO (we will implement later).");
                    break;

                case "2":
                    listContactsMenu();
                    break;

                case "3":
                    searchContactsMenu();
                    break;

                case "0":
                    System.out.println("Logging out...");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }

    private void listContactsMenu()
    {
        System.out.println("\n-- List Contacts --");
        System.out.println("Sortable fields: contact_id, first_name, last_name, nickname, email, birth_date");
        System.out.print("Enter sort field (or leave empty for contact_id): ");

        String sortField = scanner.nextLine().trim();
        if (sortField.isEmpty())
        {
            sortField = "contact_id";
        }

        System.out.print("Sort ascending? (Y/N): ");
        String orderInput = scanner.nextLine().trim();
        boolean ascending = !orderInput.equalsIgnoreCase("N");

        List<Contact> contacts = contactDao.getAllContacts(sortField, ascending);

        if (contacts.isEmpty())
        {
            System.out.println("No contacts found.");
        }
        else
        {
            System.out.println("\n--- CONTACT LIST ---");
            for (Contact c : contacts)
            {
                System.out.println(c.toString());
            }
        }
    }

    private void searchContactsMenu()
    {
        System.out.println("\n-- Search Contacts (Single Field) --");
        System.out.println("Searchable fields: first_name, last_name, nickname, email, phone, linkedin");
        System.out.print("Enter field name: ");
        String fieldName = scanner.nextLine().trim();

        System.out.print("Enter keyword to search: ");
        String keyword = scanner.nextLine().trim();

        if (fieldName.isEmpty() || keyword.isEmpty())
        {
            System.out.println("Field name and keyword cannot be empty.");
            return;
        }

        List<Contact> contacts = contactDao.searchByField(fieldName, keyword);

        if (contacts.isEmpty())
        {
            System.out.println("No contacts matched your criteria.");
        }
        else
        {
            System.out.println("\n--- SEARCH RESULTS ---");
            for (Contact c : contacts)
            {
                System.out.println(c.toString());
            }
        }
    }
}
