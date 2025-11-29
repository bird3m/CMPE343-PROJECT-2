
public class AuthService
{
    private final UserDao userDao;

    public AuthService()
    {
        this.userDao = new UserDao();
    }

    public User login(String username, String passwordPlain)
    {
        if (username == null || username.isBlank())
        {
            System.out.println("Username cannot be empty.");
            return null;
        }

        if (passwordPlain == null || passwordPlain.isBlank())
        {
            System.out.println("Password cannot be empty.");
            return null;
        }

        User user = userDao.findByUsername(username);

        if (user == null)
        {
            System.out.println("User not found.");
            return null;
        }

        // Şimdilik plain text (ileride hash fonksiyonu ekleyeceğiz)
        if (!passwordPlain.equals(user.getPasswordHash()))
        {
            System.out.println("Invalid password.");
            return null;
        }

        return user;
    }
}
