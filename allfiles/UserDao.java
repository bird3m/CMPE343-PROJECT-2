import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) responsible for performing all database operations
 * related to {@link User} objects.
 * <p>
 * Provides CRUD operations, password updates, and helper methods for converting
 * SQL query results into User domain objects.
 * <p>
 * All interactions rely on {@link DatabaseConnection} to obtain a live JDBC connection.
 */
public class UserDao
{
    /* ============================ SQL QUERIES ============================ */

    private static final String FIND_BY_USERNAME_SQL =
        "SELECT user_id, username, password_hash, password_salt, name, surname, role, created_at "
            + "FROM users WHERE username = ?";

    private static final String FIND_BY_ID_SQL =
        "SELECT user_id, username, password_hash, password_salt, name, surname, role, created_at "
            + "FROM users WHERE user_id = ?";

    private static final String FIND_ALL_SQL =
        "SELECT user_id, username, password_hash, password_salt, name, surname, role, created_at "
            + "FROM users ORDER BY user_id";

    private static final String INSERT_USER_SQL =
        "INSERT INTO users (username, password_hash, password_salt, name, surname, role, created_at) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_USER_SQL =
        "UPDATE users SET username = ?, password_hash = ?, password_salt = ?, name = ?, surname = ?, role = ? "
            + "WHERE user_id = ?";

    private static final String DELETE_USER_SQL =
        "DELETE FROM users WHERE user_id = ?";


    /* ============================ QUERY METHODS ============================ */

    /**
     * Retrieves a user from the database by username.
     *
     * @param username the username to search for
     * @return a {@link User} object if found, otherwise {@code null}
     */
    public User findByUsername(String username)
    {
        Connection conn = DatabaseConnection.getConnection();

        if (conn == null)
        {
            System.out.println("UserDao: Could not obtain database connection.");
            return null;
        }

        try (PreparedStatement ps = conn.prepareStatement(FIND_BY_USERNAME_SQL))
        {
            ps.setString(1, username);

            try (ResultSet rs = ps.executeQuery())
            {
                if (!rs.next())
                {
                    return null;
                }
                return extractUserFromResultSet(rs);
            }
        }
        catch (SQLException e)
        {
            System.out.println("UserDao: SQL error while finding user by username.");
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Retrieves a user by its unique ID.
     *
     * @param userId the ID of the user to retrieve
     * @return a {@link User} object if found, or {@code null} if not found
     */
    public User findById(int userId)
    {
        Connection conn = DatabaseConnection.getConnection();

        if (conn == null)
        {
            System.out.println("UserDao: Could not obtain database connection.");
            return null;
        }

        try (PreparedStatement ps = conn.prepareStatement(FIND_BY_ID_SQL))
        {
            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery())
            {
                if (!rs.next())
                {
                    return null;
                }
                return extractUserFromResultSet(rs);
            }
        }
        catch (SQLException e)
        {
            System.out.println("UserDao: SQL error while finding user by ID.");
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Returns a list of all users stored in the database.
     *
     * @return a list of {@link User} objects; empty list if none found or on error
     */
    public List<User> findAll()
    {
        List<User> users = new ArrayList<>();
        Connection conn = DatabaseConnection.getConnection();

        if (conn == null)
        {
            System.out.println("UserDao: Could not obtain database connection.");
            return users;
        }

        try (PreparedStatement ps = conn.prepareStatement(FIND_ALL_SQL);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                User user = extractUserFromResultSet(rs);
                if (user != null)
                {
                    users.add(user);
                }
            }
        }
        catch (SQLException e)
        {
            System.out.println("UserDao: SQL error while finding all users.");
            System.out.println("Error: " + e.getMessage());
        }

        return users;
    }

    /**
     * Inserts a new user into the database.
     *
     * @param user the user entity to persist
     * @return {@code true} if the user was successfully added, {@code false} otherwise
     */
    public boolean save(User user)
    {
        Connection conn = DatabaseConnection.getConnection();

        if (conn == null)
        {
            System.out.println("UserDao: Could not obtain database connection.");
            return false;
        }

        try (PreparedStatement ps = conn.prepareStatement(INSERT_USER_SQL))
        {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getPasswordSalt() != null ? user.getPasswordSalt() : "");
            ps.setString(4, user.getName());
            ps.setString(5, user.getSurname());
            ps.setString(6, user.getRole().toString());

            LocalDateTime createdAt = user.getCreatedAt() != null
                                      ? user.getCreatedAt()
                                      : LocalDateTime.now();
            ps.setTimestamp(7, Timestamp.valueOf(createdAt));

            int inserted = ps.executeUpdate();
            return inserted > 0;
        }
        catch (SQLException e)
        {
            System.out.println("UserDao: SQL error while inserting user.");
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates an existing user’s details.
     *
     * @param user a fully populated user object containing updated fields
     * @return {@code true} if the update succeeded, {@code false} otherwise
     */
    public boolean update(User user)
    {
        Connection conn = DatabaseConnection.getConnection();

        if (conn == null)
        {
            System.out.println("UserDao: Could not obtain database connection.");
            return false;
        }

        try (PreparedStatement ps = conn.prepareStatement(UPDATE_USER_SQL))
        {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getPasswordSalt() != null ? user.getPasswordSalt() : "");
            ps.setString(4, user.getName());
            ps.setString(5, user.getSurname());
            ps.setString(6, user.getRole().toString());
            ps.setInt(7, user.getUserId());

            int updated = ps.executeUpdate();
            return updated > 0;
        }
        catch (SQLException e)
        {
            System.out.println("UserDao: SQL error while updating user.");
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Deletes a user from the database by ID.
     *
     * @param userId the ID of the user to remove
     * @return {@code true} if at least one record was deleted, {@code false} otherwise
     */
    public boolean delete(int userId)
    {
        Connection conn = DatabaseConnection.getConnection();

        if (conn == null)
        {
            System.out.println("UserDao: Could not obtain database connection.");
            return false;
        }

        try (PreparedStatement ps = conn.prepareStatement(DELETE_USER_SQL))
        {
            ps.setInt(1, userId);

            int deleted = ps.executeUpdate();
            return deleted > 0;
        }
        catch (SQLException e)
        {
            System.out.println("UserDao: SQL error while deleting user.");
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    /**
     * Updates only the password fields (hash & salt) for a specific username.
     * <p>
     * Used when the user changes their password via profile or admin operation.
     *
     * @param username the username whose password should be updated
     * @param newHash  the newly computed PBKDF2 password hash
     * @param newSalt  the cryptographic salt used to hash the password
     * @return {@code true} if the update succeeded, {@code false} otherwise
     */
    public boolean updatePassword(String username, String newHash, String newSalt)
    {
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null)
        {
            System.out.println("UserDao: Could not obtain database connection.");
            return false;
        }

        String sql = "UPDATE users SET password_hash = ?, password_salt = ? WHERE username = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, newHash);
            ps.setString(2, newSalt);
            ps.setString(3, username);

            int updated = ps.executeUpdate();
            return updated > 0;
        }
        catch (SQLException e)
        {
            System.out.println("UserDao: SQL error in updatePassword: " + e.getMessage());
            return false;
        }
    }


    /* ============================ HELPER METHODS ============================ */

    /**
     * Converts a ResultSet row into a fully populated {@link User} object.
     * <p>
     * This method is shared by all finder queries.
     *
     * @param rs the active ResultSet positioned at a valid row
     * @return a populated User entity
     * @throws SQLException if column extraction fails
     */
    private User extractUserFromResultSet(ResultSet rs) throws SQLException
    {
        User user = new User();
        user.setUserId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPasswordHash(rs.getString("password_hash"));
        user.setPasswordSalt(rs.getString("password_salt"));
        user.setName(rs.getString("name"));
        user.setSurname(rs.getString("surname"));

        String roleStr = rs.getString("role");
        if (roleStr != null)
        {
            try
            {
                user.setRole(Role.valueOf(roleStr));
            }
            catch (IllegalArgumentException e)
            {
                System.out.println("Warning: Invalid role value in database: " + roleStr);
            }
        }

        if (rs.getTimestamp("created_at") != null)
        {
            try
            {
                user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            }
            catch (Exception ignored)
            {
                // Ignore timestamp parsing errors
            }
        }

        return user;
    }
}
