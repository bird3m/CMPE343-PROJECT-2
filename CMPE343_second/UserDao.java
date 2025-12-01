import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UserDao
{
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

    /**
     * Username ile kullanıcı bulma
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
     * ID ile kullanıcı bulma
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
     * Tüm kullanıcıları listeleme
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
     * Yeni kullanıcı ekleme
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
            
            LocalDateTime createdAt = user.getCreatedAt() != null ? user.getCreatedAt() : LocalDateTime.now();
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
     * Kullanıcı güncelleme
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
     * Kullanıcı silme
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
     * Şifre güncelleme
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

    /**
     * ResultSet'ten User objesi oluşturma (yardımcı metod)
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
                Role role = Role.valueOf(roleStr);
                user.setRole(role);
            }
            catch (IllegalArgumentException e)
            {
                System.out.println("Warning: Invalid role value: " + roleStr);
            }
        }

        try
        {
            if (rs.getTimestamp("created_at") != null)
            {
                user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            }
        }
        catch (Exception e)
        {
            // Ignore timestamp parsing errors
        }

        return user;
    }
}