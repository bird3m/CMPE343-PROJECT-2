import java.time.LocalDate;

public class JuniorDeveloperMenu extends AbstractContactMenu
{
    /**
     * Constructor for the Junior Developer menu.
     */
    public JuniorDeveloperMenu(User currentUser)
    {
        super(currentUser);
    }

    /**
     * Prints the menu header for the Junior Developer.
     */
    @Override
    protected void printMenuHeader()
    {
        System.out.println("=== JUNIOR DEVELOPER MENU ===");
    }

    /**
     * Prints the available menu options.
     */
    @Override
    protected void printMenuOptions()
    {
        System.out.println("1) Change password");
        System.out.println("2) List all contacts (with sorting)");
        System.out.println("3) Search contacts");
        System.out.println("4) Update existing contact");
        System.out.println("5) Undo the last operation");
        System.out.println("0) Logout");
    }

    /**
     * Handles the user’s menu choice.
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
     * Updates an existing contact record.
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

        /**
         * Copies the old contact for undo.
         */
        Contact before = copyContact(c);

        System.out.println("Current contact: " + c.toString());
        System.out.println("Press ENTER to keep existing value.\n");

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
                System.out.println("Invalid e-mail format. Keeping old value.");
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
                System.out.println("Invalid LinkedIn URL. Keeping old value.");
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
            if (!InputValidator.isValidIsoDate(bd))
            {
                System.out.println("Invalid date format. Keeping old value.");
            }
            else
            {
                c.setBirthDate(LocalDate.parse(bd));
            }
        }

        boolean ok = contactDao.updateContact(c);
        if (ok)
        {
            System.out.println("Contact updated successfully.");

            /**
             * Saves undo action.
             */
            UndoManager.add(new UndoableCommand()
            {
                @Override
                public void undo()
                {
                    contactDao.updateContact(before);
                    System.out.println("Undo: contact reverted to previous state.");
                }
            });
        }
        else
        {
            System.out.println("Failed to update contact.");
        }
    }
}

