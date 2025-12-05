import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides database operations for the contacts table.
 * Includes CRUD functions and various search queries.
 */
public class ContactDao
{
    private static final String BASE_SELECT =
        "SELECT contact_id, first_name, middle_name, last_name, nickname, " +
        "phone_primary, phone_secondary, email, linkedin_url, birth_date, " +
        "created_at, updated_at FROM contacts";

    /**
     * Normalizes the column name for sorting.
     */
    private String normalizeSortColumn(String sortColumn)
    {
        if (sortColumn == null)
        {
            return "contact_id";
        }

        String col = sortColumn.toLowerCase();

        switch (col)
        {
            case "first_name":
                return "first_name";
            case "last_name":
                return "last_name";
            case "nickname":
                return "nickname";
            case "email":
                return "email";
            case "birth_date":
                return "birth_date";
            default:
                return "contact_id";
        }
    }

    /**
     * Normalizes user-provided field names for searching.
     */
    private String normalizeSearchColumn(String fieldName)
    {
        if (fieldName == null)
        {
            return null;
        }

        String f = fieldName.toLowerCase();

        switch (f)
        {
            case "first_name":
            case "firstname":
            case "first":
                return "first_name";

            case "last_name":
            case "lastname":
            case "last":
                return "last_name";

            case "nickname":
            case "nick":
                return "nickname";

            case "email":
                return "email";

            case "phone":
            case "phone_primary":
                return "phone_primary";

            case "linkedin":
            case "linkedin_url":
                return "linkedin_url";

            default:
                return null;
        }
    }

    /**
     * Returns all contacts sorted by a given field.
     */
    public List<Contact> getAllContacts(String sortColumn, boolean ascending)
    {
        List<Contact> result = new ArrayList<>();

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null)
        {
            System.out.println("ContactDao: Could not obtain database connection.");
            return result;
        }

        String orderBy = normalizeSortColumn(sortColumn);
        String sortOrder = ascending ? "ASC" : "DESC";

        String sql = BASE_SELECT + " ORDER BY " + orderBy + " " + sortOrder;

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                Contact c = mapRowToContact(rs);
                result.add(c);
            }
        }
        catch (SQLException e)
        {
            System.out.println("ContactDao: SQL error in getAllContacts: " + e.getMessage());
        }

        return result;
    }

    /**
     * Searches contacts by a single field and keyword.
     */
    public List<Contact> searchByField(String fieldName, String keyword)
    {
        List<Contact> result = new ArrayList<>();

        String column = normalizeSearchColumn(fieldName);
        if (column == null)
        {
            System.out.println("ContactDao: Unsupported search field: " + fieldName);
            return result;
        }

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null)
        {
            System.out.println("ContactDao: Could not obtain database connection.");
            return result;
        }

        String sql = BASE_SELECT + " WHERE " + column + " LIKE ?";

        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, "%" + keyword + "%");

            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    Contact c = mapRowToContact(rs);
                    result.add(c);
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("ContactDao: SQL error in searchByField: " + e.getMessage());
        }

        return result;
    }

    /**
     * Finds a contact by its ID.
     */
    public Contact getById(int contactId)
    {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null)
        {
            System.out.println("ContactDao: Could not obtain database connection.");
            return null;
        }

        String sql = BASE_SELECT + " WHERE contact_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, contactId);

            try (ResultSet rs = ps.executeQuery())
            {
                if (rs.next())
                {
                    return mapRowToContact(rs);
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("ContactDao: SQL error in getById: " + e.getMessage());
        }

        return null;
    }

    /**
     * Updates a contact record in the database.
     */
    public boolean updateContact(Contact c)
    {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null)
        {
            System.out.println("ContactDao: Could not obtain database connection.");
            return false;
        }

        String sql =
            "UPDATE contacts SET first_name = ?, middle_name = ?, last_name = ?, " +
            "nickname = ?, phone_primary = ?, phone_secondary = ?, email = ?, " +
            "linkedin_url = ?, birth_date = ?, updated_at = CURRENT_TIMESTAMP " +
            "WHERE contact_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getMiddleName());
            ps.setString(3, c.getLastName());
            ps.setString(4, c.getNickname());
            ps.setString(5, c.getPhonePrimary());
            ps.setString(6, c.getPhoneSecondary());
            ps.setString(7, c.getEmail());
            ps.setString(8, c.getLinkedinUrl());

            if (c.getBirthDate() != null)
            {
                ps.setDate(9, Date.valueOf(c.getBirthDate()));
            }
            else
            {
                ps.setDate(9, null);
            }

            ps.setInt(10, c.getContactId());

            int updated = ps.executeUpdate();
            return updated > 0;
        }
        catch (SQLException e)
        {
            System.out.println("ContactDao: SQL error in updateContact: " + e.getMessage());
            return false;
        }
    }

    /**
     * Inserts a new contact and returns the generated ID.
     */
    public int insertContact(Contact c)
    {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null)
        {
            System.out.println("ContactDao: Could not obtain database connection.");
            return -1;
        }

        String sql =
            "INSERT INTO contacts " +
            "(first_name, middle_name, last_name, nickname, phone_primary, " +
            "phone_secondary, email, linkedin_url, birth_date, created_at, updated_at) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try (PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS))
        {
            ps.setString(1, c.getFirstName());
            ps.setString(2, c.getMiddleName());
            ps.setString(3, c.getLastName());
            ps.setString(4, c.getNickname());
            ps.setString(5, c.getPhonePrimary());
            ps.setString(6, c.getPhoneSecondary());
            ps.setString(7, c.getEmail());
            ps.setString(8, c.getLinkedinUrl());

            if (c.getBirthDate() != null)
            {
                ps.setDate(9, Date.valueOf(c.getBirthDate()));
            }
            else
            {
                ps.setDate(9, null);
            }

            int affected = ps.executeUpdate();
            if (affected == 0)
            {
                return -1;
            }

            try (ResultSet keys = ps.getGeneratedKeys())
            {
                if (keys.next())
                {
                    return keys.getInt(1); // new contact ID
                }
            }

            return -1;
        }
        catch (SQLException e)
        {
            System.out.println("ContactDao: SQL error in insertContact: " + e.getMessage());
            return -1;
        }
    }

    /**
     * Deletes a contact by ID.
     */
    public boolean deleteContact(int contactId)
    {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null)
        {
            System.out.println("ContactDao: Could not obtain database connection.");
            return false;
        }

        String sql = "DELETE FROM contacts WHERE contact_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, contactId);
            int deleted = ps.executeUpdate();
            return deleted > 0;
        }
        catch (SQLException e)
        {
            System.out.println("ContactDao: SQL error in deleteContact: " + e.getMessage());
            return false;
        }
    }

    /**
     * Maps a ResultSet row to a Contact object.
     */
    private Contact mapRowToContact(ResultSet rs) throws SQLException
    {
        Contact c = new Contact();

        c.setContactId(rs.getInt("contact_id"));
        c.setFirstName(rs.getString("first_name"));
        c.setMiddleName(rs.getString("middle_name"));
        c.setLastName(rs.getString("last_name"));
        c.setNickname(rs.getString("nickname"));
        c.setPhonePrimary(rs.getString("phone_primary"));
        c.setPhoneSecondary(rs.getString("phone_secondary"));
        c.setEmail(rs.getString("email"));
        c.setLinkedinUrl(rs.getString("linkedin_url"));

        Date birth = rs.getDate("birth_date");
        if (birth != null)
        {
            c.setBirthDate(birth.toLocalDate());
        }

        Timestamp created = rs.getTimestamp("created_at");
        if (created != null)
        {
            c.setCreatedAt(created.toLocalDateTime());
        }

        Timestamp updated = rs.getTimestamp("updated_at");
        if (updated != null)
        {
            c.setUpdatedAt(updated.toLocalDateTime());
        }

        return c;
    }

    /**
     * Searches contacts by first name and birth month.
     */
    public List<Contact> searchFirstNameAndBirthMonth(String firstName, int month)
    {
        List<Contact> result = new ArrayList<>();

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null)
        {
            System.out.println("ContactDao: Could not obtain database connection.");
            return result;
        }

        String sql = BASE_SELECT +
            " WHERE first_name = ? " +
            " AND birth_date IS NOT NULL " +
            " AND MONTH(birth_date) = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, firstName);
            ps.setInt(2, month);

            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    result.add(mapRowToContact(rs));
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("ContactDao: SQL error in searchFirstNameAndBirthMonth: " + e.getMessage());
        }

        return result;
    }

    /**
     * Performs a multi-field search using AND logic.
     */
    public List<Contact> searchByMultipleFields(List<String> fields, List<String> keywords)
    {
        List<Contact> result = new ArrayList<>();

        if (fields == null || keywords == null || fields.size() != keywords.size())
        {
            return result;
        }

        Connection conn = DatabaseConnection.getConnection();
        if (conn == null)
        {
            System.out.println("ContactDao: Could not obtain database connection.");
            return result;
        }

        StringBuilder sql = new StringBuilder(BASE_SELECT + " WHERE ");

        for (int i = 0; i < fields.size(); i++)
        {
            String column = normalizeSearchColumn(fields.get(i));
            if (column == null)
            {
                System.out.println("ContactDao: Unsupported field: " + fields.get(i));
                return new ArrayList<>();
            }

            sql.append(column).append(" LIKE ?");
            if (i < fields.size() - 1)
            {
                sql.append(" AND ");
            }
        }

        try (PreparedStatement ps = conn.prepareStatement(sql.toString()))
        {
            for (int i = 0; i < keywords.size(); i++)
            {
                ps.setString(i + 1, "%" + keywords.get(i) + "%");
            }

            try (ResultSet rs = ps.executeQuery())
            {
                while (rs.next())
                {
                    result.add(mapRowToContact(rs));
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("ContactDao: SQL error in searchByMultipleFields: " + e.getMessage());
        }

        return result;
    }

    public boolean restoreContactWithSameId(Contact c)
    {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null)
        {
            System.out.println("ContactDao: Could not obtain database connection.");
            return false;
        }

        String sql =
            "INSERT INTO contacts (" +
            "contact_id, first_name, middle_name, last_name, nickname, " +
            "phone_primary, phone_secondary, email, linkedin_url, birth_date, " +
            "created_at, updated_at) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";

        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setInt(1, c.getContactId());
            ps.setString(2, c.getFirstName());
            ps.setString(3, c.getMiddleName());
            ps.setString(4, c.getLastName());
            ps.setString(5, c.getNickname());
            ps.setString(6, c.getPhonePrimary());
            ps.setString(7, c.getPhoneSecondary());
            ps.setString(8, c.getEmail());
            ps.setString(9, c.getLinkedinUrl());

            if (c.getBirthDate() != null)
            {
                ps.setDate(10, Date.valueOf(c.getBirthDate()));
            }
            else
            {
                ps.setDate(10, null); // null → DB default / NULL
            }

            int inserted = ps.executeUpdate();
            return inserted > 0;
        }
        catch (SQLException e)
        {
            System.out.println("ContactDao: SQL error in restoreContactWithSameId: " + e.getMessage());
            return false;
        }
}


}
