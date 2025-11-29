public class SeniorDeveloperMenu
{
    private final User currentUser;

    public SeniorDeveloperMenu(User currentUser)
    {
        this.currentUser = currentUser;
    }

    public void start()
    {
        System.out.println("[SENIOR DEV MENU] Logged in as: " + currentUser.getUsername());
    }
}
