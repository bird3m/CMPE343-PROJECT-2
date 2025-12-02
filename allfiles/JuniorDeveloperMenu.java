import java.time.LocalDate;

public class JuniorDeveloperMenu extends AbstractContactMenu
{
    public JuniorDeveloperMenu(User currentUser)
    {
        super(currentUser);
    }

    @Override
    protected void printMenuHeader()
    {
        System.out.println("=== JUNIOR DEVELOPER MENU ===");
    }

    @Override
    protected void printMenuOptions()
    {
        System.out.println("1) Change password");
        System.out.println("2) List all contacts (with sorting)");
        System.out.println("3) Search contacts");
        System.out.println("4) Update existing contact");
         System.out.println("5) Undo");
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
                listContactsWithSorting();
                return true;

            case "3":
                showSearchMenu();
                return true;

            case "4":
                updateContactMenu();
                return true;
            
             case "5":
                UndoManager.undoLast();   // 🔥 Burada son işlemi geri alıyoruz
                return true;

            case "0":
                System.out.println("Logging out...");
                return false;

            default:
                System.out.println("Invalid option, try again.");
                return true;
        }
    }

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

        // UNDO için eski halin kopyasını al
        Contact before = copyContact(c);



        System.out.println("Current contact: " + c.toString());
        System.out.println("Press ENTER to keep existing value.\n");

        System.out.print("First name [" + c.getFirstName() + "]: ");
        String fn = scanner.nextLine().trim();
        if (!fn.isEmpty())
        {
            c.setFirstName(fn);
        }

        System.out.print("Middle name [" + c.getMiddleName() + "]: ");
        String mn = scanner.nextLine().trim();
        if (!mn.isEmpty())
        {
            c.setMiddleName(mn);
        }

        System.out.print("Last name [" + c.getLastName() + "]: ");
        String ln = scanner.nextLine().trim();
        if (!ln.isEmpty())
        {
            c.setLastName(ln);
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
            c.setPhonePrimary(p1);
        }

        System.out.print("Secondary phone [" + c.getPhoneSecondary() + "]: ");
        String p2 = scanner.nextLine().trim();
        if (!p2.isEmpty())
        {
            c.setPhoneSecondary(p2);
        }

        System.out.print("Email [" + c.getEmail() + "]: ");
        String em = scanner.nextLine().trim();
        if (!em.isEmpty())
        {
            c.setEmail(em);
        }

        System.out.print("LinkedIn URL [" + c.getLinkedinUrl() + "]: ");
        String li = scanner.nextLine().trim();
        if (!li.isEmpty())
        {
            c.setLinkedinUrl(li);
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

            // 🔙 UNDO kaydı
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
