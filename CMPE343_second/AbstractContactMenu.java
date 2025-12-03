import java.util.ArrayList;
import java.util.List;
/**
 * Base abstract menu class for all contact-related menu screens.
 * Shared by Tester, Junior Developer, Senior Developer, and Manager menus.
 * Provides common search and list operations.
 */

public abstract class AbstractContactMenu extends BaseMenu
{
     /** Data access object for performing contact queries. */
    protected final ContactDao contactDao;
     /**
     * Constructor that initializes the menu for the current user.
     *
     * @param currentUser the logged-in user
     */

    protected AbstractContactMenu(User currentUser)
    {
        super(currentUser);
        this.contactDao = new ContactDao();
    }

    // -------------------------------------------------
    // 1) List contacts with sorting (Tester/Junior/Senior)
    // -------------------------------------------------
    /**
     * Lists all contacts with sorting options.
     * User can choose the field and sort direction.
     * If nothing is entered, sorting defaults to contact_id.
     */
    protected void listContactsWithSorting()
    {
        System.out.println("\n-- List Contacts --");
        System.out.println("Sortable fields: contact_id, first_name, last_name, nickname, email, birth_date");
        System.out.print("Sort field (ENTER = contact_id): ");

        String sortField = scanner.nextLine().trim();
        if (sortField.isEmpty())
        {
            sortField = "contact_id";
        }

        System.out.print("Ascending? (Y/N): ");
        String sort = scanner.nextLine().trim();
        boolean ascending = !sort.equalsIgnoreCase("N");

        List<Contact> contacts = contactDao.getAllContacts(sortField, ascending);

        if (contacts.isEmpty())
        {
            System.out.println("No contacts found.");
            return;
        }

        System.out.println("\n--- CONTACTS ---");
        for (Contact c : contacts)
        {
            System.out.println(c);
        }
    }

    // -------------------------------------------------
    // 2) Single-field search
    // -------------------------------------------------
    /**
     * Performs a single-field search.
     * User selects one field and enters a keyword.
     * Supports partial matching.
     */
    protected void searchContactsSingleField()
    {
        System.out.println("\n-- Search Contacts (Single Field) --");
        System.out.println("Fields: first_name, last_name, nickname, email, phone, linkedin");
        System.out.print("Field: ");
        String field = scanner.nextLine().trim();

        System.out.print("Keyword: ");
        String keyword = scanner.nextLine().trim();

        if (field.isEmpty() || keyword.isEmpty())
        {
            System.out.println("Field and keyword cannot be empty.");
            return;
        }

        List<Contact> contacts = contactDao.searchByField(field, keyword);

        if (contacts.isEmpty())
        {
            System.out.println("No contacts matched.");
            return;
        }

        System.out.println("\n--- RESULTS ---");
        for (Contact c : contacts)
        {
            System.out.println(c);
        }
    }

    // -------------------------------------------------
    // 3) Multi-field search (AND)  → PDF'teki "multiple fields" kısmı
    // -------------------------------------------------
      /**
     * Performs a multi-field search using AND logic.
     * This corresponds to the "multiple fields search" requirement in the project PDF.
     * User can enter up to 3 field=keyword conditions.
     * Only contacts matching all conditions will be returned.
     */
    protected void searchContactsMultiField()
    {
        System.out.println("\n-- Search Contacts (Multiple Fields, AND) --");
        System.out.println("Fields you can use: first_name, last_name, nickname, email, phone, linkedin");
        System.out.println("You can enter up to 3 field=keyword pairs.");
        System.out.println("Example: first_name=Jon");

        List<String> fields = new ArrayList<>();
        List<String> keywords = new ArrayList<>();

        for (int i = 1; i <= 3; i++)
        {
            System.out.print("Condition " + i + " (ENTER to stop): ");
            String line = scanner.nextLine().trim();
            if (line.isEmpty())
            {
                break;
            }

            String[] parts = line.split("=", 2);
            if (parts.length != 2)
            {
                System.out.println("Invalid format. Use field=keyword (e.g. last_name=Stark).");
                i--;
                continue;
            }

            String field = parts[0].trim();
            String keyword = parts[1].trim();

            if (field.isEmpty() || keyword.isEmpty())
            {
                System.out.println("Field and keyword cannot be empty.");
                i--;
                continue;
            }

            fields.add(field);
            keywords.add(keyword);
        }

        if (fields.isEmpty())
        {
            System.out.println("No conditions entered.");
            return;
        }

       // 🔥 Key line: this calls the multi-field search method inside the DAO

        List<Contact> result = contactDao.searchByMultipleFields(fields, keywords);

        if (result.isEmpty())
        {
            System.out.println("No contacts matched all conditions.");
            return;
        }

        System.out.println("\n--- MULTI-FIELD SEARCH RESULTS ---");
        for (Contact c : result)
        {
            System.out.println(c);
        }
    }
}
