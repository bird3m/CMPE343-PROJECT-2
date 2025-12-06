import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Menu implementation for the {@link Role#MANAGER} user role.
 * <p>
 * A manager can:
 * <ul>
 *     <li>Change their own password (via {@link BaseMenu}</li>
 *     <li>View statistical information about contacts</li>
 *     <li>List all users</li>
 *     <li>Add (employ) new users</li>
 *     <li>Update existing users</li>
 *     <li>Delete (fire) existing users</li>
 * </ul>
 * This class extends {@link BaseMenu} and uses {@link UserDao} and
 * {@link ContactDao} to perform database operations.
 */
public class ManagerMenu extends BaseMenu
{
    /** Data access object for contacts, used for statistics. */
    private final ContactDao contactDao;

    /** Data access object for user management operations. */
    private final UserDao userDao;

    /**
     * Creates a new manager menu for the given logged-in user.
     *
     * @param currentUser the currently authenticated user (must have MANAGER role)
     */
    public ManagerMenu(User currentUser)
    {
        super(currentUser);
        this.contactDao = new ContactDao();
        this.userDao = new UserDao();
    }

    /**
     * Prints the header for the manager menu.
     * <p>
     * Called by the base menu framework when rendering the menu.
     */
    @Override
    protected void printMenuHeader()
    {
        System.out.println("=== MANAGER MENU ===");
    }

    /**
     * Prints the list of available menu options for the manager role.
     */
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

    /**
     * Handles a single menu choice entered by the manager.
     *
     * @param choice the raw menu option string entered by the user
     * @return {@code true} to keep the menu loop running, {@code false} to exit to the previous screen
     */
    @Override
    protected boolean handleChoice(String choice)
    {
        switch (choice)
        {
            case "1":
                // Change password for the current user (implemented in BaseMenu)
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
     * Lists all users currently stored in the system.
     * <p>
     * Fetches all users through {@link UserDao#findAll()} and prints a simple table
     * containing ID, username, name, surname, role, and creation timestamp.
     * If no users exist, an informational message is printed instead.
     */
    private void listAllUsers()
    {
        System.out.println("\n=== ALL USERS ===");

        try
        {
            List<User> allUsers = userDao.findAll();

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
     * Starts the flow for adding/employing a new user.
     * <p>
     * Prompts the manager for username, password, name, surname, and role,
     * performs basic validation (e.g. non-empty fields, unique username),
     * hashes the password using {@link PasswordHasher}, and persists the new
     * user using {@link UserDao#save(User)}.
     */
    private void addNewUser()
    {
        System.out.println("\n=== ADD NEW USER ===");

        try
        {
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
            String passwordHash = PasswordHasher.hashPassword(password, salt);

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
            boolean success = userDao.save(newUser);

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
     * Starts the flow for updating an existing user.
     * <p>
     * The manager selects a user by ID and then chooses which field to update:
     * username, password, name, surname or role. After modification, the
     * changes are persisted using {@link UserDao#update(User)}.
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

            User user = userDao.findById(userId);

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

            boolean success = userDao.update(user);

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
     * Starts the flow for deleting/firing an existing user.
     * <p>
     * The manager selects a user by ID, confirms the deletion, and then
     * the user is removed via {@link UserDao#delete(int)}. A manager
     * cannot delete themselves.
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

            User user = userDao.findById(userId);

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

            boolean success = userDao.delete(userId);

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

    /**
     * Displays basic statistical information about contacts.
     * <p>
     * Statistics include:
     * <ul>
     *     <li>Total number of contacts</li>
     *     <li>Number of contacts grouped by last name</li>
     *     <li>Number of contacts grouped by email domain</li>
     *     <li>Earliest and latest birth date among contacts (if available)</li>
     * </ul>
     * Data is obtained via {@link ContactDao#getAllContacts(String, boolean)}.
     */
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
