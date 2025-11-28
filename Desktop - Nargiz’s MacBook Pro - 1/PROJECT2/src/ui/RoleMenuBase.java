package ui;

import model.User;
import dao.UserDAO;
import dao.ContactDAO;
import java.util.Scanner;

/**
 * Abstract base class (Abstraction) for all user role menus.
 */
public abstract class RoleMenuBase {
    protected User currentUser;
    protected Scanner scanner = new Scanner(System.in);
    protected UserDAO userDAO = new UserDAO();
    protected ContactDAO contactDAO = new ContactDAO();

    public RoleMenuBase(User user) {
        this.currentUser = user;
    }

    /**
     * Displays the specific menu for the role (Polymorphism).
     */
    public abstract void displayMenu();

    /**
     * Common operation: Changes the user's password.
     */
    public void changePassword() {
        System.out.println("\n--- CHANGE PASSWORD ---");
        System.out.print("Enter New Password: ");
        String newPass = scanner.nextLine();
        System.out.print("Confirm New Password: ");
        String confirmPass = scanner.nextLine();

        if (!newPass.equals(confirmPass)) {
            System.err.println("Error: Passwords do not match.");
            return;
        }
        
        if (userDAO.updatePassword(currentUser.getUserId(), newPass)) {
            System.out.println("✅ Password updated successfully.");
        } else {
            System.err.println("❌ Failed to update password.");
        }
    }
    
    /**
     * Common operation: Logs the user out[cite: 27].
     */
    public void logout() {
        System.out.println(currentUser.getFullName() + " logging out...");
    }
    
    /**
     * Handles safe integer input to prevent application crashes[cite: 51, 52].
     */
    protected int safeInput(String prompt, int maxChoice) {
        int choice = -1;
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= maxChoice) {
                    return choice;
                } else {
                    System.err.println("Error: Please enter a number between 1 and " + maxChoice + ".");
                }
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid data type. Please enter a number.");
            }
        }
    }
}