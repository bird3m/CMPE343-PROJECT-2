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

    // -------------------------------------------------
    // 1) List contacts with sorting (Tester/Junior/Senior)
    // -------------------------------------------------
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

    // -------------------------------------------------
    // 2) Search menu (single + multi field) – terminal screenshot formatı
    // -------------------------------------------------
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

    // -------------------------------------------------
    // Ortak çıktı fonksiyonu
    // -------------------------------------------------
    protected void printContactList(List<Contact> contacts)
    {
        if (contacts == null || contacts.isEmpty())
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

        // ---- Helper: Contact kopyala (undo için) ----
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

