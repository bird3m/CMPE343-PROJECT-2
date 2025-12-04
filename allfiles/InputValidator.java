import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class InputValidator
{
    /**
     * Unicode letters only pattern (2–50 characters).
     */
    private static final Pattern NAME_PATTERN =
        Pattern.compile("^[\\p{L}]{2,50}$");

    /**
     * Validates a name using the Unicode letter pattern.
     */
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
     * Regex for email pattern, checking the format.
     */
    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    /**
     * Validates an email address using the email regex.
     */
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
     * Regex for birthdate pattern, checking the format YYYY-MM-DD.
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

    /**
     * Regex for LinkedIn profile URL format.
     */
    private static final Pattern LINKEDIN_PATTERN =
        Pattern.compile("^https://(www\\.)?linkedin\\.com/in/[A-Za-z0-9_-]+/?$");

    /**
     * Validates a LinkedIn profile URL.
     */
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

    /**
     * Regex for phone numbers (digits only).
     */
    private static final Pattern PHONE_PATTERN =
        Pattern.compile("^\\d{10,15}$");

    /**
     * Validates a phone number (digits only, 10–15 characters).
     */
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

        return PHONE_PATTERN.matcher(trimmed).matches();
    }
}