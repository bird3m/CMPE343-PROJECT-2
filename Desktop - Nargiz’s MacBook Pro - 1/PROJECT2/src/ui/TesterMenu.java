package ui;

import model.User;
import model.Contact;
import java.util.List;

/**
 * Menu implementation for the Tester role (Inheritance from RoleMenuBase).
 * Permissions: change password, logout, list all contacts, search, sort[cite: 27].
 */
public class TesterMenu extends RoleMenuBase {

    public TesterMenu(User user) {
        super(user);
    }

    @Override
    public void displayMenu() {
        int choice = -1;
        while (choice != 5) {
            // Displays user name and surname at the top[cite: 21].
            System.out.println("\n*** Tester Menu - User: " + currentUser.getFullName() + " ***"); 
            System.out.println("1. List All Contacts");
            System.out.println("2. Search Contacts (Single/Multi-Field)");
            System.out.println("3. Sort Contact Results");
            System.out.println("4. Change Password");
            System.out.println("5. Logout");
            
            choice = safeInput("Select an option: ", 5);

            switch (choice) {
                case 1: listAllContacts(); break;
                case 2: searchContacts(); break;
                case 3: sortContacts(); break;
                case 4: changePassword(); break;
                case 5: logout(); return; // Exit loop and return to login screen
            }
        }
    }

    private void listAllContacts() {
        System.out.println("\n--- ALL CONTACTS LIST ---");
        List<Contact> contacts = contactDAO.getAllContacts();
        
        // Print Header
        System.out.println("--------------------------------------------------------------------------------------------------");
        System.out.println(String.format("| %-4s | %-15s | %-15s | %-15s | %-30s | %-10s |", 
                                        "ID", "First Name", "Last Name", "Primary Phone", "Email", "Birth Date"));
        System.out.println("--------------------------------------------------------------------------------------------------");
        
        // Print Data
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            for (Contact contact : contacts) {
                System.out.println(contact);
            }
        }
        System.out.println("Total records: " + contacts.size());
    }
    
    private void searchContacts() {
        // Needs detailed sub-menu for 3 single-field and 3 multi-field searches[cite: 29].
        System.out.println("\n--- CONTACT SEARCH ---");
        System.out.println("Search functionality is complex and needs a sub-menu here.");
        // Example: contactDAO.searchContacts("first_name", "Jon");
    }

    private void sortContacts() {
        // Needs detailed menu to select field and order (ASC/DESC)[cite: 27].
        System.out.println("\n--- SORT RESULTS ---");
        System.out.println("Sorting functionality needs a sub-menu here.");
        // Example: contactDAO.sortContacts("birth_date", "ASC");
    }
}