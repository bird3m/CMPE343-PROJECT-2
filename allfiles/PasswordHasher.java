import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHasher
{
    /**
     * Generates a random salt and returns it as a Base64 string.
     */
    public static String generateSalt()
    {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Hash using PBKDF2WithHmacSHA256 with the provided password and salt.
     * This is the method signature called by ManagerMenu.
     */
    public static String hashPassword(String password, String salt)
    {
        try
        {
            PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(),
                Base64.getDecoder().decode(salt),
                20000,          // iteration count
                256             // key length
            );

            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = skf.generateSecret(spec).getEncoded();

            return Base64.getEncoder().encodeToString(hash);
        }
        catch (Exception e)
        {
            // Generally, these errors are not thrown; the application is stopped or logged.
            throw new RuntimeException("Hashing failed: " + e.getMessage(), e);
        }
    }
}
