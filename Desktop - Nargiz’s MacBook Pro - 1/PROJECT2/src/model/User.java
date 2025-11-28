package model;

import java.time.LocalDateTime;

/**
 * Model class for the 'users' table.
 */
public class User {
    private int userId;
    private String username;
    private String name;
    private String surname;
    private Role role;
    private LocalDateTime createdAt;

    // Constructor
    public User(int userId, String username, String name, String surname, Role role, LocalDateTime createdAt) {
        this.userId = userId;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.role = role;
        this.createdAt = createdAt;
    }

    // Getter Methods
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public Role getRole() { return role; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    /**
     * Returns the user's full name, supporting Turkish characters[cite: 21].
     */
    public String getFullName() {
        return name + " " + surname;
    }
}