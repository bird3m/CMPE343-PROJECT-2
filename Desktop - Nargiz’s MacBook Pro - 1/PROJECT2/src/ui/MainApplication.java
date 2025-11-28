package ui;

import model.User;
import dao.UserDAO;

import java.util.Scanner;

/**
 * Main application class, managing the login screen and user session.
 */
public class MainApplication {
    private UserDAO userDAO = new UserDAO();
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        // Display ASCII animation at startup[cite: 16].
        System.out.println("\n\t\t\n");
        System.out.println("___ROL TABANLI İLETİŞİM YÖNETİM SİSTEMİ___");
        
        // The application should work under any condition unless terminated[cite: 18].
        while (true) {
            User currentUser = login();
            
            if (currentUser != null) {
                // Successful login: display the corresponding role menu[cite: 20].
                RoleMenuFactory.getRoleMenu(currentUser).displayMenu();
                // When logout() is called inside the menu, it returns here.
            } else {
                // Unsuccessful login: prompt user to retry[cite: 22].
                System.out.println("Incorrect credentials. Please try again.");
                System.out.println("Type 'exit' to terminate the application.");
                
                String input = scanner.nextLine();
                if (input.trim().equalsIgnoreCase("exit")) {
                    break;
                }
            }
        }
        
        // Display ASCII animation at shutdown[cite: 16].
        System.out.println("\nApplication Shutting Down. Goodbye...");
        System.out.println("\n\t\t");
        scanner.close();
    }

    private User login() {
        System.out.println("\n--- LOGIN SCREEN ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine(); 

        // Authenticate via DAO
        return userDAO.authenticate(username, password);
    }
    
    public static void main(String[] args) {
        new MainApplication().start();
    }
}