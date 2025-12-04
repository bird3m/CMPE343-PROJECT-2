import java.time.LocalDate;
/**
 * Menu class for the Senior Developer role.
 */

public class SeniorDeveloperMenu extends AbstractContactMenu
{
    /**
     * Creates the menu for the senior developer.
     */
    public SeniorDeveloperMenu(User currentUser)
    {
        super(currentUser);
    }
   /**
     * Prints the menu header.
     */
    @Override
    protected void printMenuHeader()
    {
        System.out.println("=== SENIOR DEVELOPER MENU ===");
    }
    /**
     * Displays the menu options.
     */
    @Override
    protected void printMenuOptions()
    {
        System.out.println("1) Change password");
        System.out.println("2) List all contacts (with sorting)");
        System.out.println("3) Search contacts");
        System.out.println("4) Update existing contact");
        System.out.println("5) Add new contact");
        System.out.println("6) Delete existing contact");
         System.out.println("7) Undo last operation");
        System.out.println("0) Logout");
    }
   /**
     * Handles the selected menu action.
     */
    @Override
    protected boolean handleChoice(String choice)
    {
        switch (choice)
        {
            case "1":
                changePasswordFlow();
                return true;

            case "2":
                listContactsWithSorting();
                return true;

            case "3":
                showSearchMenu();
                return true;

            case "4":
                updateContactMenu();
                return true;

            case "5":
                addContactMenu();
                return true;

            case "6":
                deleteContactMenu();
                return true;

            case "7":
                UndoManager.undoLast();
                return true;

            case "0":
                System.out.println("Logging out...");
                return false;

            default:
                System.out.println("Invalid option, try again.");
                return true;
        }
    }
    /**
     * Updates an existing contact.
     */
    private void updateContactMenu()
    {
        System.out.print("\nEnter contact ID to update: ");
        String idStr = scanner.nextLine().trim();

        int id;
        try
        {
            id = Integer.parseInt(idStr);
        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid ID.");
            return;
        }

               Contact c = contactDao.getById(id);
        if (c == null)
        {
            System.out.println("Contact not found.");
            return;
        }

        // Take a copy of the previous state for UNDO
        Contact before = copyContact(c);


        System.out.println("Current contact: " + c.toString());
        System.out.println("Press ENTER to keep existing value.\n");

        // FIRST NAME
        System.out.print("First name [" + c.getFirstName() + "]: ");
        String fn = scanner.nextLine().trim();
        if (!fn.isEmpty())
        {
            if (!InputValidator.isValidName(fn))
            {
                System.out.println("Invalid first name. Keeping old value.");
            }
            else
            {
                c.setFirstName(fn);
            }
        }

        // MIDDLE NAME
        System.out.print("Middle name [" + c.getMiddleName() + "]: ");
        String mn = scanner.nextLine().trim();
        if (!mn.isEmpty())
        {
            if (!InputValidator.isValidName(mn))
            {
                System.out.println("Invalid middle name. Keeping old value.");
            }
            else
            {
                c.setMiddleName(mn);
            }
        }

        // LAST NAME
        System.out.print("Last name [" + c.getLastName() + "]: ");
        String ln = scanner.nextLine().trim();
        if (!ln.isEmpty())
        {
            if (!InputValidator.isValidName(ln))
            {
                System.out.println("Invalid last name. Keeping old value.");
            }
            else
            {
                c.setLastName(ln);
            }
        }


        System.out.print("Nickname [" + c.getNickname() + "]: ");
        String nn = scanner.nextLine().trim();
        if (!nn.isEmpty())
        {
            c.setNickname(nn);
        }

        System.out.print("Primary phone [" + c.getPhonePrimary() + "]: ");
        String p1 = scanner.nextLine().trim();
        if (!p1.isEmpty())
        {
            if (!InputValidator.isValidPhone(p1))
            {
                System.out.println("Invalid phone number. Keeping old primary phone.");
            }
            else
            {
                c.setPhonePrimary(p1);
            }
        }

        System.out.print("Secondary phone [" + c.getPhoneSecondary() + "]: ");
        String p2 = scanner.nextLine().trim();
        if (!p2.isEmpty())
        {
            if (!InputValidator.isValidPhone(p2))
            {
                System.out.println("Invalid phone number. Keeping old secondary phone.");
            }
            else
            {
                c.setPhoneSecondary(p2);
            }
        }


        System.out.print("Email [" + c.getEmail() + "]: ");
        String em = scanner.nextLine().trim();
        if (!em.isEmpty())
        {
            if (!InputValidator.isValidEmail(em))
            {
                System.out.println("Invalid e-mail format. Example: user@example.com");
                System.out.println("Keeping old e-mail value.");
            }
            else
            {
                c.setEmail(em);
            }
        }


        System.out.print("LinkedIn URL [" + c.getLinkedinUrl() + "]: ");
        String li = scanner.nextLine().trim();
        if (!li.isEmpty())
        {
            if (!InputValidator.isValidLinkedInUrl(li))
            {
                System.out.println("Invalid LinkedIn URL.");
                System.out.println("Expected format: https://www.linkedin.com/in/username");
                System.out.println("Keeping old LinkedIn URL.");
            }
            else
            {
                c.setLinkedinUrl(li);
            }
        }


        System.out.print("Birth date (YYYY-MM-DD) [" + c.getBirthDate() + "]: ");
        String bd = scanner.nextLine().trim();
        if (!bd.isEmpty())
        {
            try
            {
                c.setBirthDate(LocalDate.parse(bd));
            }
            catch (Exception e)
            {
                System.out.println("Invalid date, keeping old value.");
            }
        }

        boolean ok = contactDao.updateContact(c);
        if (ok)
        {
            System.out.println("Contact updated successfully.");
        }
        else
        {
            System.out.println("Failed to update contact.");
        }
    }

    /**
     * Adds a new contact.
     */
    private void addContactMenu()
    {
        Contact c = new Contact();

        System.out.println("\n-- Add New Contact --");

        // FIRST NAME (mandatory)
        String firstName;
        while (true)
        {
            System.out.print("First name: ");
            firstName = scanner.nextLine().trim();

            if (InputValidator.isValidName(firstName))
            {
                break;
            }

            System.out.println("Invalid first name. Use only letters.");
        }
        c.setFirstName(firstName);

        // MIDDLE NAME (optional but will be saved if valid)
        String middleName = null;
        while (true)
        {
            System.out.print("Middle name (optional): ");
            String mn = scanner.nextLine().trim();

            if (mn.isEmpty())
            {
                middleName = null;
                break;
            }

            if (!InputValidator.isValidName(mn))
            {
                System.out.println("Invalid middle name. Use only letters.");
                continue;
            }

            middleName = mn;
            break;
        }
        c.setMiddleName(middleName);

        // LAST NAME (MANDATORY)
        String lastName;
        while (true)
        {
            System.out.print("Last name: ");
            lastName = scanner.nextLine().trim();

            if (InputValidator.isValidName(lastName))
            {
                break;
            }

            System.out.println("Invalid last name. Use only letters.");
        }
        c.setLastName(lastName);


        System.out.print("Nickname (optional): ");
        String nn = scanner.nextLine().trim();
        c.setNickname(nn.isEmpty() ? null : nn);

        // Primary phone (MANDATORY)
        String primaryPhone;
        while (true)
        {
            System.out.print("Primary phone (digits only, e.g., 05001110001): ");
            primaryPhone = scanner.nextLine().trim();

            if (InputValidator.isValidPhone(primaryPhone))
            {
                break;
            }

            System.out.println("Invalid phone number. Use 10-15 digits, no spaces.");
        }
        c.setPhonePrimary(primaryPhone);

        // Secondary phone (OPTIONAL but must be valid)
        String secondaryPhone;
        while (true)
        {
            System.out.print("Secondary phone (optional, ENTER to skip): ");
            secondaryPhone = scanner.nextLine().trim();

            if (secondaryPhone.isEmpty())
            {
                secondaryPhone = null;
                break;
            }

            if (!InputValidator.isValidPhone(secondaryPhone))
            {
                System.out.println("Invalid phone number. Use 10-15 digits, no spaces.");
                continue;
            }

            break;
        }
        c.setPhoneSecondary(secondaryPhone);


        System.out.print("Secondary phone (optional): ");
        String p2 = scanner.nextLine().trim();
        c.setPhoneSecondary(p2.isEmpty() ? null : p2);

        String email;
        while (true)
        {
            System.out.print("Email: ");
            email = scanner.nextLine().trim();

            if (InputValidator.isValidEmail(email))
            {
                break;
            }

            System.out.println("Invalid e-mail format. Example: user@example.com");
        }
        c.setEmail(email);


    String linkedIn;
    while (true)
    {
        System.out.print("LinkedIn URL (optional, ENTER to skip)\n"
            + "Expected format: https://www.linkedin.com/in/username\n"
            + "LinkedIn: ");
        linkedIn = scanner.nextLine().trim();

        if (linkedIn.isEmpty())
        {
            // The user wants to leave it blank → null
            linkedIn = null;
            break;
        }

        if (!InputValidator.isValidLinkedInUrl(linkedIn))
        {
            System.out.println("Invalid LinkedIn URL. Please follow the format above.");
            continue;
        }

        break;
    }
    c.setLinkedinUrl(linkedIn);


    LocalDate birthDate;
    while (true)
    {
        System.out.print("Birth date (YYYY-MM-DD): ");
        String bd = scanner.nextLine().trim();

        if (!InputValidator.isValidIsoDate(bd))
        {
            System.out.println("Invalid date format. Expected YYYY-MM-DD and a real date.");
            continue;
        }

        birthDate = LocalDate.parse(bd);
        break;
    }
    
    c.setBirthDate(birthDate);

    int newId = contactDao.insertContact(c);

        if (newId > 0)
        {
            System.out.println("Contact inserted successfully.");

            // UNDO RECORD = Delete the added contact
            UndoManager.add(new UndoableCommand()
            {
                @Override
                public void undo()
                {
                    contactDao.deleteContact(newId);
                    System.out.println("Undo: added contact removed.");
                }
            });
        }
        else
        {
            System.out.println("Failed to insert contact.");
        }

    }
    /**
     * Adds a new contact.
     */
    private void deleteContactMenu()
    {
        System.out.print("\nEnter contact ID to delete: ");
        String idStr = scanner.nextLine().trim();

        int id;
        try
        {
            id = Integer.parseInt(idStr);
        }
        catch (NumberFormatException e)
        {
            System.out.println("Invalid ID.");
            return;
        }

        // 1) Back up your contacts before deleting them
        Contact backup = contactDao.getById(id);

        if (backup == null)
        {
            System.out.println("Contact not found.");
            return;
        }

        // 2) Deletion process
        boolean ok = contactDao.deleteContact(id);
        if (ok)
        {
            System.out.println("Contact deleted.");

            // 3) UNDO record, backup contact will be added back
            UndoManager.add(new UndoableCommand()
            {
                @Override
                public void undo()
                {
                    contactDao.insertContact(backup);
                    System.out.println("Undo: deleted contact restored.");
                }
            });
        }
        else
        {
            System.out.println("Failed to delete contact (maybe not found).");
        }

    }
}