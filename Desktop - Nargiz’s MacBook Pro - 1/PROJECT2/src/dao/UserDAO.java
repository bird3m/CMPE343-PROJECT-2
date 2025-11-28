package dao;

import model.User;
import model.Role;
import utils.DatabaseConnection;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Data Access Object for the 'users' table, handling authentication and password hashing.
 */
public class UserDAO {

    /**
     * Authenticates a user by checking the provided password against the stored hash.
     * @param username The username entered.
     * @param password The raw password entered.
     * @return User object on success, null otherwise.
     */
    public User authenticate(String username, String password) {
        String sql = "SELECT user_id, password_hash, name, surname, role, created_at FROM users WHERE username = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");

                // CRITICAL: Check the raw password against the stored hash
                if (BCrypt.checkpw(password, storedHash)) {
                    return new User(
                        rs.getInt("user_id"),
                        username,
                        rs.getString("name"),
                        rs.getString("surname"),
                        Role.valueOf(rs.getString("role").toUpperCase()),
                        rs.getTimestamp("created_at").toLocalDateTime()
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Database or query error during authentication: " + e.getMessage());
        } catch (NoClassDefFoundError e) {
             System.err.println("❌ ERROR: BCrypt library (jbcrypt-0.4.jar) not found!");
        }
        return null;
    }
    
    /**
     * Updates the user's password using a new BCrypt hash.
     * @param userId The ID of the user to update.
     * @param newPassword The new raw password.
     * @return true if update was successful.
     */
    public boolean updatePassword(int userId, String newPassword) {
        String newHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        String sql = "UPDATE users SET password_hash = ? WHERE user_id = ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, newHash);
            pstmt.setInt(2, userId);
            
            return pstmt.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.err.println("Password update error: " + e.getMessage());
            return false;
        }
    }
}