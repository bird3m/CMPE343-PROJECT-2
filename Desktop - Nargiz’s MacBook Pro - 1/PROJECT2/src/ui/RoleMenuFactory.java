package ui;

import model.User;

/**
 * Factory class to instantiate the correct RoleMenu based on the User's role.
 */
public class RoleMenuFactory {
    public static RoleMenuBase getRoleMenu(User user) {
        
        if (user == null) {
            throw new IllegalArgumentException("User object cannot be null.");
        }
        
        switch (user.getRole()) {
            case TESTER:
                return new TesterMenu(user);
            case JUNIOR_DEV:
                // TODO: Create JuniorDeveloperMenu extends RoleMenuBase
                return new TesterMenu(user); 
            case SENIOR_DEV:
                // TODO: Create SeniorDeveloperMenu extends JuniorDeveloperMenu
                return new TesterMenu(user); 
            case MANAGER:
                // TODO: Create ManagerMenu extends RoleMenuBase
                return new TesterMenu(user); 
            default:
                throw new IllegalArgumentException("Undefined User Role: " + user.getRole());
        }
    }
}