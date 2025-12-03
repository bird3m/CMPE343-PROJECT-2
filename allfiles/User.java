import java.time.LocalDateTime;
/**
 * Represents a system user.
 */
public class User
{
    private int userId;
    private String username;
    private String passwordHash;
    private String passwordSalt;
    private String name;
    private String surname;
    private Role role;
    private LocalDateTime createdAt;

    public int getUserId()
    {
        return userId;
    }

    public void setUserId(int userId)
    {
        this.userId = userId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPasswordHash()
    {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash)
    {
        this.passwordHash = passwordHash;
    }

    public String getPasswordSalt()
    {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt)
    {
        this.passwordSalt = passwordSalt;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getSurname()
    {
        return surname;
    }

    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;
    }

    @Override
    public String toString()
    {
        return "User{"
            + "userId=" + userId
            + ", username='" + username + '\''
            + ", name='" + name + '\''
            + ", surname='" + surname + '\''
            + ", role=" + role
            + '}';
    }
}
