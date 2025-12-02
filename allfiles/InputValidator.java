import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class InputValidator
{
    private static final Pattern NAME_PATTERN =
    // Unicode harfler + boşluk + . ' -
    Pattern.compile("^[\\p{L}]{2,50}$");

    public static boolean isValidName(String name)
    {
        if (name == null)
        {
            return false;
        }

        String trimmed = name.trim();
        if (trimmed.isEmpty())
        {
            return false;
        }

        return NAME_PATTERN.matcher(trimmed).matches();
    }

    /**
     * regex for email pattern, checking the format
    */

    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public static boolean isValidEmail(String email)
    {
        if (email == null)
        {
            return false;
        }
        String trimmed = email.trim();
        if (trimmed.isEmpty())
        {
            return false;
        }
        return EMAIL_PATTERN.matcher(trimmed).matches();
    }

    /**
     * regex for birthdate pattern, checking the format
     */
    public static boolean isValidIsoDate(String text)
    {
        if (text == null)
        {
            return false;
        }

        String trimmed = text.trim();
        if (!trimmed.matches("\\d{4}-\\d{2}-\\d{2}"))
        {
            return false;
        }

        try
        {
            LocalDate.parse(trimmed, DateTimeFormatter.ISO_LOCAL_DATE);
            return true;
        }
        catch (DateTimeParseException e)
        {
            return false;
        }
    }

    private static final Pattern LINKEDIN_PATTERN =
    Pattern.compile("^https://(www\\.)?linkedin\\.com/in/[A-Za-z0-9_-]+/?$");

    public static boolean isValidLinkedInUrl(String url)
    {
        if (url == null)
        {
            return false;
        }

        String trimmed = url.trim();
        if (trimmed.isEmpty())
        {
            return false;
        }

        return LINKEDIN_PATTERN.matcher(trimmed).matches();
    }

    
    private static final Pattern PHONE_PATTERN =
    Pattern.compile("^\\d{10,15}$");

    public static boolean isValidPhone(String phone)
    {
        if (phone == null)
        {
            return false;
        }

        String trimmed = phone.trim();
        if (trimmed.isEmpty())
        {
            return false;
        }

        //takes digits only
        return PHONE_PATTERN.matcher(trimmed).matches();
    }


}
