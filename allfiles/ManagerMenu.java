import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Manager menu class, does all the operations that the manager user should do. Inherits from the Base Menu.
 */
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
        System.out.println("3) List all users");
        System.out.println("4) Add/employ new user");
        System.out.println("5) Update existing user");
        System.out.println("6) Delete/fire existing user");
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
                listAllUsers();
                return true;

            case "4":
                addNewUser();
                return true;

            case "5":
                updateExistingUser();
                return true;

            case "6":
                deleteExistingUser();
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
     * Lists all users
     */
    private void listAllUsers()
    {
        System.out.println("\n=== ALL USERS ===");
        
        try
        {
            List<User> allUsers = userDao.findAll(); // UserDao'dan doğru çağrı
            
            if (allUsers == null || allUsers.isEmpty())
            {
                System.out.println("No users found in the system.");
                return;
            }

            System.out.println("Total users: " + allUsers.size());
            System.out.println("─────────────────────────────────────────────────────────────────────");
            System.out.printf("%-5s %-15s %-20s %-20s %-15s %-20s%n", 
                             "ID", "Username", "Name", "Surname", "Role", "Created At");
            System.out.println("─────────────────────────────────────────────────────────────────────");

            for (User user : allUsers)
            {
                System.out.printf("%-5d %-15s %-20s %-20s %-15s %-20s%n",
                                user.getUserId(),
                                user.getUsername(),
                                user.getName(),
                                user.getSurname(),
                                user.getRole().toString(),
                                user.getCreatedAt() != null ? user.getCreatedAt().toString() : "N/A");
            }
            System.out.println("─────────────────────────────────────────────────────────────────────");
        }
        catch (Exception e)
        {
            System.out.println("Error listing users: " + e.getMessage());
        }
    }

    /**
     * Add new users
     */
    private void addNewUser()
    {
        System.out.println("\n=== ADD NEW USER ===");
        
        try
        {
            // Tüm girdi alma işlemleri BaseMenu'dan gelen getInputWithPrompt() ile yapılıyor.
            String username = getInputWithPrompt("Enter username: "); 
            
            if (username.isEmpty())
            {
                System.out.println("Username cannot be empty!");
                return;
            }

            User existingUser = userDao.findByUsername(username);
            if (existingUser != null)
            {
                System.out.println("Username already exists! Please choose a different username.");
                return;
            }

            String password = getInputWithPrompt("Enter password: ");
            
            if (password.isEmpty())
            {
                System.out.println("Password cannot be empty!");
                return;
            }

            String name = getInputWithPrompt("Enter name: ");
            
            if (name.isEmpty())
            {
                System.out.println("Name cannot be empty!");
                return;
            }

            String surname = getInputWithPrompt("Enter surname: ");
            
            if (surname.isEmpty())
            {
                System.out.println("Surname cannot be empty!");
                return;
            }

            // Role selection
            System.out.println("Select role:");
            System.out.println("1) TESTER");
            System.out.println("2) JUNIOR_DEVELOPER");
            System.out.println("3) SENIOR_DEVELOPER");
            System.out.println("4) MANAGER");
            String roleChoice = getInputWithPrompt("Enter choice (1-4): ");
            
            Role role;
            
            switch (roleChoice)
            {
                case "1":
                    role = Role.TESTER;
                    break;
                case "2":
                    role = Role.JUNIOR_DEV;
                    break;
                case "3":
                    role = Role.SENIOR_DEV;
                    break;
                case "4":
                    role = Role.MANAGER;
                    break;
                default:
                    System.out.println("Invalid role selection!");
                    return;
            }

            // Salt and hash generation from PasswordHasher
            String salt = PasswordHasher.generateSalt();
            String passwordHash = PasswordHasher.hashPassword(password, salt); // PasswordHasher'da var olmalı

            // New user object
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setPasswordHash(passwordHash);
            newUser.setPasswordSalt(salt); 
            newUser.setName(name);
            newUser.setSurname(surname);
            newUser.setRole(role);
            newUser.setCreatedAt(LocalDateTime.now());

            // Save to database
            boolean success = userDao.save(newUser); // UserDao'da var
            
            if (success)
            {
                System.out.println("✓ User added successfully!");
                System.out.println("Username: " + username);
                System.out.println("Name: " + name + " " + surname);
                System.out.println("Role: " + role);
            }
            else
            {
                System.out.println("✗ Failed to add user. Please try again.");
            }
        }
        catch (Exception e)
        {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    /**
     * Updates the current user
     */
    private void updateExistingUser()
    {
        System.out.println("\n=== UPDATE EXISTING USER ===");
        
        try
        {
            listAllUsers();
            
            String input = getInputWithPrompt("\nEnter user ID to update (0 to cancel): ");
            
            if (input.equals("0"))
            {
                System.out.println("Update cancelled.");
                return;
            }

            int userId;
            try
            {
                userId = Integer.parseInt(input);
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid user ID format!");
                return;
            }

            User user = userDao.findById(userId); // UserDao'da var
            
            if (user == null)
            {
                System.out.println("User with ID " + userId + " not found!");
                return;
            }

            System.out.println("\nCurrent user information:");
            System.out.println("Username: " + user.getUsername());
            System.out.println("Name: " + user.getName());
            System.out.println("Surname: " + user.getSurname());
            System.out.println("Role: " + user.getRole());

            System.out.println("\nWhat would you like to update?");
            System.out.println("1) Username");
            System.out.println("2) Password");
            System.out.println("3) Name");
            System.out.println("4) Surname");
            System.out.println("5) Role");
            System.out.println("0) Cancel");
            
            String choice = getInputWithPrompt("Enter choice: ");

            switch (choice)
            {
                case "1":
                    String newUsername = getInputWithPrompt("Enter new username: ");
                    if (!newUsername.isEmpty())
                    {
                        User existingUser = userDao.findByUsername(newUsername);
                        if (existingUser != null && existingUser.getUserId() != userId)
                        {
                            System.out.println("Username already exists!");
                            return;
                        }
                        user.setUsername(newUsername);
                    }
                    break;

                case "2":
                    String newPassword = getInputWithPrompt("Enter new password: ");
                    if (!newPassword.isEmpty())
                    {
                        String newSalt = PasswordHasher.generateSalt();
                        String newHash = PasswordHasher.hashPassword(newPassword, newSalt);
                        user.setPasswordHash(newHash);
                        user.setPasswordSalt(newSalt);
                        System.out.println("Password hash/salt updated locally. Will save upon update.");
                    }
                    break;

                case "3":
                    String newName = getInputWithPrompt("Enter new name: ");
                    if (!newName.isEmpty())
                    {
                        user.setName(newName);
                    }
                    break;

                case "4":
                    String newSurname = getInputWithPrompt("Enter new surname: ");
                    if (!newSurname.isEmpty())
                    {
                        user.setSurname(newSurname);
                    }
                    break;

                case "5":
                    System.out.println("Select new role:");
                    System.out.println("1) TESTER");
                    System.out.println("2) JUNIOR_DEVELOPER");
                    System.out.println("3) SENIOR_DEVELOPER");
                    System.out.println("4) MANAGER");
                    String roleChoice = getInputWithPrompt("Enter choice (1-4): ");
                    
                    switch (roleChoice)
                    {
                        case "1":
                            user.setRole(Role.TESTER);
                            break;
                        case "2":
                            user.setRole(Role.JUNIOR_DEV);
                            break;
                        case "3":
                            user.setRole(Role.SENIOR_DEV);
                            break;
                        case "4":
                            user.setRole(Role.MANAGER);
                            break;
                        default:
                            System.out.println("Invalid role selection! Role not changed.");
                            return;
                    }
                    break;

                case "0":
                    System.out.println("Update cancelled.");
                    return;

                default:
                    System.out.println("Invalid choice!");
                    return;
            }

            boolean success = userDao.update(user); // UserDao'da var
            
            if (success)
            {
                System.out.println("✓ User updated successfully!");
            }
            else
            {
                System.out.println("✗ Failed to update user. Please try again.");
            }
        }
        catch (Exception e)
        {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }

    /**
     * Deletes the user
     */
    private void deleteExistingUser()
    {
        System.out.println("\n=== DELETE/FIRE EXISTING USER ===");
        
        try
        {
            listAllUsers();
            
            String input = getInputWithPrompt("\nEnter user ID to delete (0 to cancel): ");
            
            if (input.equals("0"))
            {
                System.out.println("Deletion cancelled.");
                return;
            }

            int userId;
            try
            {
                userId = Integer.parseInt(input);
            }
            catch (NumberFormatException e)
            {
                System.out.println("Invalid user ID format!");
                return;
            }

            User user = userDao.findById(userId); // UserDao'da var
            
            if (user == null)
            {
                System.out.println("User with ID " + userId + " not found!");
                return;
            }

            if (userId == currentUser.getUserId())
            {
                System.out.println("You cannot delete yourself!");
                return;
            }

            System.out.println("\nUser to be deleted:");
            System.out.println("Username: " + user.getUsername());
            System.out.println("Name: " + user.getName() + " " + user.getSurname());
            System.out.println("Role: " + user.getRole());

            String confirmation = getInputWithPrompt("\nAre you sure you want to delete this user? (yes/no): ");

            if (!confirmation.equals("yes") && !confirmation.equals("y"))
            {
                System.out.println("Deletion cancelled.");
                return;
            }

            boolean success = userDao.delete(userId); // UserDao'da var
            
            if (success)
            {
                System.out.println("✓ User deleted successfully!");
            }
            else
            {
                System.out.println("✗ Failed to delete user. Please try again.");
            }
        }
        catch (Exception e)
        {
            System.out.println("Error deleting user: " + e.getMessage());
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
            String ln = c.getLastName();
            if (ln != null) byLastName.put(ln, byLastName.getOrDefault(ln, 0) + 1);

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

            if (c.getBirthDate() != null)
            {
                LocalDate bd = c.getBirthDate();
                if (minBirth == null || bd.isBefore(minBirth)) minBirth = bd;
                if (maxBirth == null || bd.isAfter(maxBirth)) maxBirth = bd;
            }
        }

        System.out.println("\nContacts per last name:");
        for (Map.Entry<String, Integer> e : byLastName.entrySet()) System.out.println("  " + e.getKey() + ": " + e.getValue());

        System.out.println("\nContacts per email domain:");
        for (Map.Entry<String, Integer> e : byEmailDomain.entrySet()) System.out.println("  " + e.getKey() + ": " + e.getValue());

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

        /**
     * Pretty-prints user list as a colored table.
     */
    private void printUserTable(List<User> users)
    {
        final int ID_WIDTH       = 4;
        final int USERNAME_WIDTH = 16;
        final int NAME_WIDTH     = 14;
        final int SURNAME_WIDTH  = 14;
        final int ROLE_WIDTH     = 14;
        final int CREATED_WIDTH  = 19; // 2025-12-03T23:59:59

        int totalWidth = 2 + ID_WIDTH
                    + 3 + USERNAME_WIDTH
                    + 3 + NAME_WIDTH
                    + 3 + SURNAME_WIDTH
                    + 3 + ROLE_WIDTH
                    + 3 + CREATED_WIDTH
                    + 2;

        String separator = "─".repeat(totalWidth);

        // Üst çizgi
        System.out.println(ConsoleColors.CYAN + separator + ConsoleColors.RESET);

        // HEADER
        System.out.printf(
            ConsoleColors.BLUE +
            "| %-" + ID_WIDTH       + "s" +
            " | %-" + USERNAME_WIDTH + "s" +
            " | %-" + NAME_WIDTH     + "s" +
            " | %-" + SURNAME_WIDTH  + "s" +
            " | %-" + ROLE_WIDTH     + "s" +
            " | %-" + CREATED_WIDTH  + "s |\n" +
            ConsoleColors.RESET,
            "ID", "Username", "Name", "Surname", "Role", "Created At"
        );

        System.out.println(ConsoleColors.CYAN + separator + ConsoleColors.RESET);

        // SATIRLAR (zebra)
        int row = 0;

        for (User u : users)
        {
            String rowColor = (row % 2 == 0)
                ? ConsoleColors.WHITE
                : ConsoleColors.RESET;

            String createdAtStr = (u.getCreatedAt() != null)
                ? u.getCreatedAt().toString()
                : "N/A";

            System.out.printf(
                rowColor +
                "| %-" + ID_WIDTH       + "d" +
                " | %-" + USERNAME_WIDTH + "s" +
                " | %-" + NAME_WIDTH     + "s" +
                " | %-" + SURNAME_WIDTH  + "s" +
                " | %-" + ROLE_WIDTH     + "s" +
                " | %-" + CREATED_WIDTH  + "s |\n" +
                ConsoleColors.RESET,
                u.getUserId(),
                u.getUsername() != null ? u.getUsername() : "",
                u.getName()     != null ? u.getName()     : "",
                u.getSurname()  != null ? u.getSurname()  : "",
                u.getRole()     != null ? u.getRole().name() : "N/A",
                createdAtStr
            );

            row++;
        }

        System.out.println(ConsoleColors.CYAN + separator + ConsoleColors.RESET);
}

}
