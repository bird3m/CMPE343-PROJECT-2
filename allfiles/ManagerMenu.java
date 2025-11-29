public class ManagerMenu
{
    private final User currentUser;

    public ManagerMenu(User currentUser)
    {
        this.currentUser = currentUser;
    }

    public void start()
    {
        System.out.println("[MANAGER MENU] Logged in as: " + currentUser.getUsername());
    }
}
