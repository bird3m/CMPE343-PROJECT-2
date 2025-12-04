import java.time.LocalDate;
import java.time.LocalDateTime;
/**
 * Represents a contact record with personal information such as name, phone,
 * email, and timestamps. Used as the main entity in the contact management system.
 */

public class Contact
{
    private int contactId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String nickname;
    private String phonePrimary;
    private String phoneSecondary;
    private String email;
    private String linkedinUrl;
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public int getContactId()
    {
        return contactId;
    }

    public void setContactId(int contactId)
    {
        this.contactId = contactId;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public String getPhonePrimary()
    {
        return phonePrimary;
    }

    public void setPhonePrimary(String phonePrimary)
    {
        this.phonePrimary = phonePrimary;
    }

    public String getPhoneSecondary()
    {
        return phoneSecondary;
    }

    public void setPhoneSecondary(String phoneSecondary)
    {
        this.phoneSecondary = phoneSecondary;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getLinkedinUrl()
    {
        return linkedinUrl;
    }

    public void setLinkedinUrl(String linkedinUrl)
    {
        this.linkedinUrl = linkedinUrl;
    }

    public LocalDate getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate)
    {
        this.birthDate = birthDate;
    }

    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt)
    {
        this.updatedAt = updatedAt;
    }

   
    @Override
    public String toString()
    {
        return "[" + contactId + "] "
            + firstName + " "
            + (middleName != null ? middleName + " " : "")
            + lastName
            + (nickname != null && !nickname.isBlank() ? " (\"" + nickname + "\")" : "")
            + " | Phone: " + phonePrimary
            + " | Email: " + email
            + " | Birth Date: " + (birthDate != null ? birthDate.toString() : "N/A");
    }


}