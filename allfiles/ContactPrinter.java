import java.util.List;

/**
 * Utility class responsible for printing a formatted table of {@link Contact} objects
 * to the console. The table uses Unicode box-drawing characters and ANSI color codes
 * (through {@link ConsoleColors}) to create a clean, readable layout.
 */
public class ContactPrinter
{
    /** Top border of the table layout. */
    private static final String TOP =
            "┌──────┬─────────────────┬─────────────────┬──────────────┬─────────────────────────┐";

    /** Middle separator used between the header and the contact rows. */
    private static final String MID =
            "├──────┼─────────────────┼─────────────────┼──────────────┼──────────────────────────┤";

    /** Bottom border of the table layout. */
    private static final String BOTTOM =
            "└──────┴─────────────────┴─────────────────┴──────────────┴─────────────────────────┘";

    /**
     * Prints a formatted table containing a list of contacts.
     * <p>
     * Each contact is displayed with ID, first name, last name, phone number,
     * and email. Rows alternate in color for readability. If the list is empty,
     * a message is printed instead.
     *
     * @param contacts the list of contacts to display; may be empty or null
     */
    public static void printContactsTable(List<Contact> contacts)
    {
        if (contacts == null || contacts.isEmpty())
        {
            System.out.println("No contacts found.");
            return;
        }

        String header = String.format(
                "│ %-4s │ %-15s │ %-15s │ %-12s │ %-25s │",
                "ID", "First Name", "Last Name", "Phone", "Email"
        );

        System.out.println(ConsoleColors.BRIGHT_CYAN + TOP + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BRIGHT_CYAN + header + ConsoleColors.RESET);
        System.out.println(ConsoleColors.BRIGHT_CYAN + MID + ConsoleColors.RESET);

        int i = 0;
        for (Contact c : contacts)
        {
            // Apply alternating text color (no background color)
            String color = (i % 2 == 0)
                    ? ConsoleColors.BRIGHT_WHITE
                    : ConsoleColors.WHITE;

            System.out.print(color);

            System.out.printf(
                    "│ %-4d │ %-15s │ %-15s │ %-12s │ %-25s │%n",
                    c.getContactId(),
                    safe(c.getFirstName()),
                    safe(c.getLastName()),
                    safe(c.getPhonePrimary()),
                    safe(c.getEmail())
            );

            System.out.print(ConsoleColors.RESET);
            i++;
        }

        System.out.println(ConsoleColors.BRIGHT_CYAN + BOTTOM + ConsoleColors.RESET);
    }

    /**
     * Safely returns a non-null string for printing.
     * <p>
     * If the provided string is {@code null}, an empty string is returned instead.
     *
     * @param s the input string, possibly null
     * @return the input string or an empty string if null
     */
    private static String safe(String s)
    {
        return (s == null ? "" : s);
    }
}
