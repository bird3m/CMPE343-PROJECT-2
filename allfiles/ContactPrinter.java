import java.util.List;

public class ContactPrinter
{
    private static final String TOP =
            "┌──────┬─────────────────┬─────────────────┬──────────────┬─────────────────────────┐";
    private static final String MID =
            "├──────┼─────────────────┼─────────────────┼──────────────┼──────────────────────────┤";
    private static final String BOTTOM =
            "└──────┴─────────────────┴─────────────────┴──────────────┴─────────────────────────┘";

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
            // Sadece yazı rengini değiştiriyoruz, background YOK
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

    private static String safe(String s)
    {
        return (s == null ? "" : s);
    }
}
