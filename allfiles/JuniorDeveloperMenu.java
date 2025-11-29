public class JuniorDeveloperMenu
{
    private final User currentUser;

    public JuniorDeveloperMenu(User currentUser)
    {
        this.currentUser = currentUser;
    }

    public void start()
    {
        System.out.println("[JUNIOR DEV MENU] Logged in as: " + currentUser.getUsername());
    }
}
