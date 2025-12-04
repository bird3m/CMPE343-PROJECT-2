/**
 * Provides login and password update operations for users.
 */
public class AuthService
{
    private final UserDao userDao;

    public AuthService()
    {
        this.userDao = new UserDao();
    }

    // -------------------------
    // LOGIN
    // -------------------------

    /**
     * Attempts to authenticate a user with the given username and password.
     */
    public User login(String username, String plainPassword)
    {
        User user = userDao.findByUsername(username);

        if (user == null)
        {
            System.out.println("AuthService: user not found: " + username);
            return null;
        }

        if (!verifyPassword(user, plainPassword))
        {
            System.out.println("AuthService: password mismatch for user " + username);
            return null;
        }

        return user;
    }

    /**
     * Verifies a password using legacy (plain text) or PBKDF2+salt hashing.
     */
    private boolean verifyPassword(User user, String plainPassword)
    {
        String storedHash = user.getPasswordHash();
        String salt = user.getPasswordSalt();

        if (storedHash == null)
        {
            return false;
        }

        if (salt == null || salt.isBlank())
        {
            return plainPassword.equals(storedHash);
        }

        String computedHash = PasswordHasher.hashPassword(plainPassword, salt);
        return storedHash.equals(computedHash);
    }

    // -------------------------
    // CHANGE PASSWORD
    // -------------------------

    /**
     * Changes the user's password after validating old and new values.
     */
    public boolean changePassword(User currentUser,
                                  String oldPassword,
                                  String newPassword1,
                                  String newPassword2)
    {
        if (newPassword1 == null || newPassword2 == null || !newPassword1.equals(newPassword2))
        {
            System.out.println("AuthService: new passwords do not match.");
            return false;
        }

        if (newPassword1.length() < 2)
        {
            System.out.println("AuthService: new password is too short.(min 2 characters)");
            return false;
        }

        User dbUser = userDao.findByUsername(currentUser.getUsername());
        if (dbUser == null)
        {
            System.out.println("AuthService: current user not found in DB.");
            return false;
        }

        if (!verifyPassword(dbUser, oldPassword))
        {
            System.out.println("AuthService: old password is incorrect.");
            return false;
        }

        String newSalt = PasswordHasher.generateSalt();
        String newHash = PasswordHasher.hashPassword(newPassword1, newSalt);
        boolean updated = userDao.updatePassword(currentUser.getUsername(), newHash, newSalt);

        if (updated)
        {
            currentUser.setPasswordHash(newHash);
            currentUser.setPasswordSalt(newSalt);
        }

        return updated;
    }
}