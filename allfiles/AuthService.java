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

    // Eski (salt yok) + yeni (PBKDF2 + salt) şifreleri doğrulayan ortak fonksiyon
    private boolean verifyPassword(User user, String plainPassword)
    {
        String storedHash = user.getPasswordHash();
        String salt = user.getPasswordSalt();   // User sınıfında getPasswordSalt() olmalı

        if (storedHash == null)
        {
            return false;
        }

        // 1) LEGACY MODE → salt yoksa, DB'deki password_hash düz şifre kabul edilir
        if (salt == null || salt.isBlank())
        {
            return plainPassword.equals(storedHash);
        }

        // 2) NEW MODE → PBKDF2 + salt
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

        // DB'den güncel user'ı çek (hash/salt dahil)
        User dbUser = userDao.findByUsername(currentUser.getUsername());
        if (dbUser == null)
        {
            System.out.println("AuthService: current user not found in DB.");
            return false;
        }

        // Eski şifreyi doğrula (hem legacy hem hashed kullanıcılar için çalışır)
        if (!verifyPassword(dbUser, oldPassword))
        {
            System.out.println("AuthService: old password is incorrect.");
            return false;
        }

        // Yeni salt + hash üret (BURADA PasswordHasher kullanıyoruz)
        String newSalt = PasswordHasher.generateSalt();
String newHash = PasswordHasher.hashPassword(newPassword1, newSalt);
        boolean updated = userDao.updatePassword(currentUser.getUsername(), newHash, newSalt);

        if (updated)
        {
            // Memory'deki currentUser'ı da güncelle
            currentUser.setPasswordHash(newHash);
            currentUser.setPasswordSalt(newSalt);
        }

        return updated;
    }
}