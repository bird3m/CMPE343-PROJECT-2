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
                    clearScreen();
                    System.out.print("Enter first name substring: ");
                    String firstPart = scanner.nextLine().trim();
                    printContactList(contactDao.searchByField("first_name", firstPart));
                    break;

                case "2":
                    clearScreen();
                    System.out.print("Enter nickname substring: ");
                    String nickPart = scanner.nextLine().trim();
                    printContactList(contactDao.searchByField("nickname", nickPart));
                    break;

                case "3":
                    clearScreen();
                    System.out.print("Enter LinkedIn URL substring: ");
                    String linkPart = scanner.nextLine().trim();
                    printContactList(contactDao.searchByField("linkedin_url", linkPart));
                    break;

                // ---------- MULTI-FIELD ----------

                case "4":
                {
                    clearScreen();
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
                    clearScreen();
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
                    clearScreen();
                    return;

                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

/**
 * Prints and displays the contacts list.
 * @param contacts List of contacts.
 */

protected void printContactList(List<Contact> contacts)
{
    clearScreen();
    if (contacts == null || contacts.isEmpty())
    {
        System.out.println("No contacts found.");
        return;
    }

   
    final int ID_W       = 3;
    final int FIRST_W    = 10;
    final int LAST_W     = 10;
    final int NICK_W     = 12;
    final int PHONE1_W   = 11;
    final int PHONE2_W   = 11;
    final int EMAIL_W    = 20;
    final int BIRTH_W    = 10;   
    final int LINKEDIN_W = 20;

    int totalWidth = 2 + ID_W
                   + 3 + FIRST_W
                   + 3 + LAST_W
                   + 3 + NICK_W
                   + 3 + PHONE1_W
                   + 3 + PHONE2_W
                   + 3 + EMAIL_W
                   + 3 + BIRTH_W
                   + 3 + LINKEDIN_W
                   + 2;

    String separator = "─".repeat(totalWidth);

    System.out.println(ConsoleColors.CYAN + separator + ConsoleColors.RESET);
    System.out.printf(
        ConsoleColors.BLUE +
        "| %-" + ID_W       + "s" +
        " | %-" + FIRST_W   + "s" +
        " | %-" + LAST_W    + "s" +
        " | %-" + NICK_W    + "s" +
        " | %-" + PHONE1_W  + "s" +
        " | %-" + PHONE2_W  + "s" +
        " | %-" + EMAIL_W   + "s" +
        " | %-" + BIRTH_W   + "s" +
        " | %-" + LINKEDIN_W+ "s |\n" +
        ConsoleColors.RESET,
        "ID", "First", "Last", "Nick",
        "Phone 1", "Phone 2",
        "Email", "Birth Date", "LinkedIn"
    );
    System.out.println(ConsoleColors.CYAN + separator + ConsoleColors.RESET);

    int row = 0;
    for (Contact c : contacts)
    {
        String rowColor = (row % 2 == 0)
            ? ConsoleColors.WHITE
            : ConsoleColors.RESET;

        String birthStr = (c.getBirthDate() != null)
            ? c.getBirthDate().toString()
            : "";

        System.out.printf(
            rowColor +
            "| %-" + ID_W       + "d" +
            " | %-" + FIRST_W   + "s" +
            " | %-" + LAST_W    + "s" +
            " | %-" + NICK_W    + "s" +
            " | %-" + PHONE1_W  + "s" +
            " | %-" + PHONE2_W  + "s" +
            " | %-" + EMAIL_W   + "s" +
            " | %-" + BIRTH_W   + "s" +
            " | %-" + LINKEDIN_W+ "s |\n" +
            ConsoleColors.RESET,
            c.getContactId(),
            fit(nullToEmpty(c.getFirstName()),  FIRST_W),
            fit(nullToEmpty(c.getLastName()),   LAST_W),
            fit(nullToEmpty(c.getNickname()),   NICK_W),
            fit(nullToEmpty(c.getPhonePrimary()),  PHONE1_W),
            fit(nullToEmpty(c.getPhoneSecondary()),PHONE2_W),
            fit(nullToEmpty(c.getEmail()),      EMAIL_W),
            fit(birthStr,                       BIRTH_W),
            fit(nullToEmpty(c.getLinkedinUrl()),LINKEDIN_W)
        );

        row++;
    }

    System.out.println(ConsoleColors.CYAN + separator + ConsoleColors.RESET);
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
