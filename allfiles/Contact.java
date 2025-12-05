import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Represents a contact entity containing personal information such as names,
 * phone numbers, email, LinkedIn URL, birth date, and audit timestamps.
 * This class is used as the main data model in the contact management system.
 */
public class Contact
{
    /** Unique identifier of the contact. */
    private int contactId;

    /** First name of the contact. */
    private String firstName;

    /** Middle name of the contact, may be null. */
    private String middleName;

    /** Last name of the contact. */
    private String lastName;

    /** Nickname or preferred short name, optional. */
    private String nickname;

    /** Primary phone number. */
    private String phonePrimary;

    /** Secondary phone number, may be null. */
    private String phoneSecondary;

    /** Email address of the contact. */
    private String email;

    /** LinkedIn profile URL, optional. */
    private String linkedinUrl;

    /** Birth date of the contact, may be null. */
    private LocalDate birthDate;

    /** Timestamp of when the contact record was created. */
    private LocalDateTime createdAt;

    /** Timestamp of the last update to the contact record. */
    private LocalDateTime updatedAt;


    /**
     * Returns the unique contact ID.
     *
     * @return the contact ID
     */
    public int getContactId()
    {
        return contactId;
    }

    /**
     * Sets the unique contact ID.
     *
     * @param contactId ID to assign
     */
    public void setContactId(int contactId)
    {
        this.contactId = contactId;
    }

    /**
     * Returns the first name of the contact.
     *
     * @return first name
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Sets the first name of the contact.
     *
     * @param firstName first name to assign
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * Returns the middle name of the contact.
     *
     * @return middle name, or null if not provided
     */
    public String getMiddleName()
    {
        return middleName;
    }

    /**
     * Sets the middle name of the contact.
     *
     * @param middleName middle name to assign
     */
    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    /**
     * Returns the last name of the contact.
     *
     * @return last name
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Sets the last name of the contact.
     *
     * @param lastName last name to assign
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * Returns the nickname of the contact.
     *
     * @return nickname, or null if none is set
     */
    public String getNickname()
    {
        return nickname;
    }

    /**
     * Sets the nickname of the contact.
     *
     * @param nickname nickname to assign
     */
    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    /**
     * Returns the primary phone number.
     *
     * @return primary phone number
     */
    public String getPhonePrimary()
    {
        return phonePrimary;
    }

    /**
     * Sets the primary phone number.
     *
     * @param phonePrimary primary phone number to assign
     */
    public void setPhonePrimary(String phonePrimary)
    {
        this.phonePrimary = phonePrimary;
    }

    /**
     * Returns the secondary phone number.
     *
     * @return secondary phone number, or null if not provided
     */
    public String getPhoneSecondary()
    {
        return phoneSecondary;
    }

    /**
     * Sets the secondary phone number.
     *
     * @param phoneSecondary secondary phone number to assign
     */
    public void setPhoneSecondary(String phoneSecondary)
    {
        this.phoneSecondary = phoneSecondary;
    }

    /**
     * Returns the email address of the contact.
     *
     * @return email address
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Sets the email address of the contact.
     *
     * @param email email address to assign
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Returns the LinkedIn URL.
     *
     * @return LinkedIn URL, or null if not provided
     */
    public String getLinkedinUrl()
    {
        return linkedinUrl;
    }

    /**
     * Sets the LinkedIn URL.
     *
     * @param linkedinUrl LinkedIn URL to assign
     */
    public void setLinkedinUrl(String linkedinUrl)
    {
        this.linkedinUrl = linkedinUrl;
    }

    /**
     * Returns the birth date of the contact.
     *
     * @return birth date, or null if not provided
     */
    public LocalDate getBirthDate()
    {
        return birthDate;
    }

    /**
     * Sets the birth date of the contact.
     *
     * @param birthDate birth date to assign
     */
    public void setBirthDate(LocalDate birthDate)
    {
        this.birthDate = birthDate;
    }

    /**
     * Returns the timestamp when the contact was created.
     *
     * @return creation timestamp
     */
    public LocalDateTime getCreatedAt()
    {
        return createdAt;
    }

    /**
     * Sets the creation timestamp.
     *
     * @param createdAt timestamp to assign
     */
    public void setCreatedAt(LocalDateTime createdAt)
    {
        this.createdAt = createdAt;
    }

    /**
     * Returns the timestamp of the last update.
     *
     * @return last update timestamp
     */
    public LocalDateTime getUpdatedAt()
    {
        return updatedAt;
    }

    /**
     * Sets the last update timestamp.
     *
     * @param updatedAt timestamp to assign
     */
    public void setUpdatedAt(LocalDateTime updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    /**
     * Returns a formatted string representation of the contact,
     * including ID, name, nickname, phone number, email, and birth date.
     *
     * @return formatted string representation of the contact
     */
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
