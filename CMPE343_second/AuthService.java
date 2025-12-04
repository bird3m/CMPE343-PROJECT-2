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
     * Common function that verifies both old (no salt) and new (PBKDF2 + salt) passwords.
     * Note: User class must include getPasswordSalt().
     */
    private boolean verifyPassword(User user, String plainPassword)
    {
        String storedHash = user.getPasswordHash();
        String salt = user.getPasswordSalt();    

        if (storedHash == null)
        {
            return false;
        }

        /**
         * LEGACY MODE → if salt is missing, password_hash is treated as plain password.
         */
        if (salt == null || salt.isBlank())
        {
            return plainPassword.equals(storedHash);
        }

       
        /**
         * NEW MODE → PBKDF2 + salt.
         */
String computedHash = PasswordHasher.hashPassword(plainPassword, salt);
        return storedHash.equals(computedHash);
    }

    // -------------------------
    // CHANGE PASSWORD
    // -------------------------
    public boolean changePassword(User currentUser,
                                  String oldPassword,
                                  String newPassword1,
                                  String newPassword2)
    {
        if (newPassword1 == null || !newPassword1.equals(newPassword2))
        {
            System.out.println("AuthService: new passwords do not match.");
            return false;
        }

        if (newPassword1.length() < 4)
        {
            System.out.println("AuthService: new password is too short.");
            return false;
        }

         /**
         * Fetch the most recent user entry from DB (including hash/salt).
         */
        User dbUser = userDao.findByUsername(currentUser.getUsername());
        if (dbUser == null)
        {
            System.out.println("AuthService: current user not found in DB.");
            return false;
        }
       /**
         * Verify old password (works for both legacy and hashed users).
         */
        if (!verifyPassword(dbUser, oldPassword))
        {
            System.out.println("AuthService: old password is incorrect.");
            return false;
        }

        /**
         * Generate new salt + hash (using PasswordHasher).
         */
        String newSalt = PasswordHasher.generateSalt();
String newHash = PasswordHasher.hashPassword(newPassword1, newSalt);
        boolean updated = userDao.updatePassword(currentUser.getUsername(), newHash, newSalt);

        if (updated)
        {
           /**
             * Update the in-memory currentUser.
             */
            currentUser.setPasswordHash(newHash);
            currentUser.setPasswordSalt(newSalt);
        }

        return updated;
    }
}
