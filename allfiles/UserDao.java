import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao
{
    private static final String FIND_BY_USERNAME_SQL =
        "SELECT user_id, username, password_hash, name, surname, role, created_at "
            + "FROM users WHERE username = ?";

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

                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setName(rs.getString("name"));
                user.setSurname(rs.getString("surname"));

                String roleStr = rs.getString("role");
                if (roleStr != null)
                {
                    user.setRole(Role.valueOf(roleStr));
                }

                try
                {
                    if (rs.getTimestamp("created_at") != null)
                    {
                        user.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    }
                }
                catch (Exception ignored)
                {
                }

                return user;
            }
        }
        catch (SQLException e)
        {
            System.out.println("UserDao: SQL error while finding user by username.");
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
