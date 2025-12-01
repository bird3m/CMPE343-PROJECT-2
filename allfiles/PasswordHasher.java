import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class PasswordHasher
{
    /**
     * Rastgele bir tuz (salt) üretir ve Base64 string olarak döndürür.
     */
    public static String generateSalt()
    {
        byte[] salt = new byte[16];
        new SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Verilen şifreyi ve tuzu (salt) kullanarak PBKDF2WithHmacSHA256 ile hashler.
     * ManagerMenu'nün çağırdığı metot imzası budur.
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
            // Genellikle bu hatalar fırlatılmaz, uygulama durdurulur veya loglanır.
            throw new RuntimeException("Hashing failed: " + e.getMessage(), e);
        }
    }
}