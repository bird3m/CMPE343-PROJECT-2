import java.util.ArrayList;
import java.util.List;

public abstract class AbstractContactMenu extends BaseMenu
{
    protected final ContactDao contactDao;

    protected AbstractContactMenu(User currentUser)
    {
        super(currentUser);
        this.contactDao = new ContactDao();
    }

    /**
     * Lists all contacts and allows sorting by the selected field.
     * Used by Tester, Junior Developer, and Senior Developer roles.
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

        printContactList(contacts);
    }

    /**
     * Shows the search menu, including both single-field and multi-field search options.
     * Matches the terminal screenshot layout required in the project.
     */
    protected void showSearchMenu()
    {
        while (true)
        {
            System.out.println();
            System.out.println("Single-field searches:");
            System.out.println("1) By first name (substring match)");
            System.out.println("2) By nickname (substring match)");
            System.out.println("3) By LinkedIn URL (substring match)");
            System.out.println("Multi-field searches:");
            System.out.println("4) First name contains X AND LinkedIn URL contains Y");
            System.out.println("5) Nickname contains X AND LinkedIn URL contains Y");
            System.out.println("6) First name equals X AND birth month is Y (1-12)");
            System.out.println("0) Back to menu");

            System.out.print("Choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice)
            {
                // ---------- SINGLE-FIELD ----------

                case "1":
                    System.out.print("Enter first name substring: ");
                    String firstPart = scanner.nextLine().trim();
                    printContactList(contactDao.searchByField("first_name", firstPart));
                    break;

                case "2":
                    System.out.print("Enter nickname substring: ");
                    String nickPart = scanner.nextLine().trim();
                    printContactList(contactDao.searchByField("nickname", nickPart));
                    break;

                case "3":
                    System.out.print("Enter LinkedIn URL substring: ");
                    String linkPart = scanner.nextLine().trim();
                    printContactList(contactDao.searchByField("linkedin_url", linkPart));
                    break;

                // ---------- MULTI-FIELD ----------

                case "4":
                {
                    System.out.print("First name contains: ");
                    String firstX = scanner.nextLine().trim();
                    System.out.print("LinkedIn URL contains: ");
                    String linkY = scanner.nextLine().trim();

                    List<String> fields = new ArrayList<>();
                    fields.add("first_name");
                    fields.add("linkedin_url");

                    List<String> keywords = new ArrayList<>();
                    keywords.add(firstX);
                    keywords.add(linkY);

                    printContactList(contactDao.searchByMultipleFields(fields, keywords));
                    break;
                }

                case "5":
                {
                    System.out.print("Nickname contains: ");
                    String nickX = scanner.nextLine().trim();
                    System.out.print("LinkedIn URL contains: ");
                    String linkY = scanner.nextLine().trim();

                    List<String> fields = new ArrayList<>();
                    fields.add("nickname");
                    fields.add("linkedin_url");

                    List<String> keywords = new ArrayList<>();
                    keywords.add(nickX);
                    keywords.add(linkY);

                    printContactList(contactDao.searchByMultipleFields(fields, keywords));
                    break;
                }

                case "6":
                {
                    System.out.print("First name (exact match): ");
                    String firstExact = scanner.nextLine().trim();
                    System.out.print("Birth month (1-12): ");
                    String monthStr = scanner.nextLine().trim();

                    try
                    {
                        int month = Integer.parseInt(monthStr);
                        printContactList(
                            contactDao.searchFirstNameAndBirthMonth(firstExact, month)
                        );
                    }
                    catch (NumberFormatException ex)
                    {
                        System.out.println("Invalid month. Please enter a number between 1 and 12.");
                    }
                    break;
                }

                case "0":
                    return;

                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

/**
 * 
 * @param contacts
 */

protected void printContactList(List<Contact> contacts)
{
    if (contacts == null || contacts.isEmpty())
    {
        System.out.println("No contacts found.");
        return;
    }

    // -------------------------------
    // Column widths
    // -------------------------------
    final int ID_WIDTH    = 4;
    final int FN_WIDTH    = 12;
    final int LN_WIDTH    = 12;
    final int PHONE_WIDTH = 12;
    final int EMAIL_WIDTH = 32;

    // -------------------------------
    // Build total separator length
    // -------------------------------
    int totalWidth = 2 + ID_WIDTH
                   + 3 + FN_WIDTH
                   + 3 + LN_WIDTH
                   + 3 + PHONE_WIDTH
                   + 3 + EMAIL_WIDTH
                   + 2;

    String separator = "-".repeat(totalWidth);

    System.out.println(ConsoleColors.CYAN + separator + ConsoleColors.RESET);

    // -------------------------------
    // HEADER (colored)
    // -------------------------------
    System.out.printf(
            ConsoleColors.YELLOW +
            "| %-" + ID_WIDTH + "s"
          + " | %-" + FN_WIDTH + "s"
          + " | %-" + LN_WIDTH + "s"
          + " | %-" + PHONE_WIDTH + "s"
          + " | " + ConsoleColors.YELLOW + "%-" + EMAIL_WIDTH + "s"
          + ConsoleColors.CYAN + " |\n" +
            ConsoleColors.RESET,
            "ID", "First Name", "Last Name", "Phone", "Email"
    );

    System.out.println(ConsoleColors.CYAN + separator + ConsoleColors.RESET);

    // -------------------------------
    // ROWS 
    // -------------------------------
    int rowIndex = 0;

    for (Contact c : contacts)
    {
        String rowColor = (rowIndex % 2 == 0)
                ? ConsoleColors.WHITE
                : ConsoleColors.CYAN;

        System.out.printf(
                rowColor +
                "| %-" + ID_WIDTH + "s"
              + " | %-" + FN_WIDTH + "s"
              + " | %-" + LN_WIDTH + "s"
              + " | %-" + PHONE_WIDTH + "s"
              + " | %-" + EMAIL_WIDTH + "s |\n" +
                ConsoleColors.RESET,
                c.getContactId(),
                c.getFirstName(),
                c.getLastName(),
                c.getPhonePrimary(),
                c.getEmail()
        );

        rowIndex++;
    }

    System.out.println(ConsoleColors.GRAY + separator + ConsoleColors.RESET);
}



    /**
     * Helper function that creates a deep copy of a Contact object.
     * Used for undo functionality.
     *
     * @param src the source contact to copy
     * @return a copied Contact instance
     */
    protected Contact copyContact(Contact src)
    {
        if (src == null) return null;

        Contact c = new Contact();
        c.setContactId(src.getContactId());
        c.setFirstName(src.getFirstName());
        c.setMiddleName(src.getMiddleName());
        c.setLastName(src.getLastName());
        c.setNickname(src.getNickname());
        c.setPhonePrimary(src.getPhonePrimary());
        c.setPhoneSecondary(src.getPhoneSecondary());
        c.setEmail(src.getEmail());
        c.setLinkedinUrl(src.getLinkedinUrl());
        c.setBirthDate(src.getBirthDate());
        c.setCreatedAt(src.getCreatedAt());
        c.setUpdatedAt(src.getUpdatedAt());
        return c;
    }
}
