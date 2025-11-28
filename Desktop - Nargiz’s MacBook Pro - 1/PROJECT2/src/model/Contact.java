package model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * contacts tablosunu temsil eden model sınıfı. Proje gereksinimlerine göre tüm alanları içerir.
 */
public class Contact {
    private int contactId;
    private String firstName;
    private String middleName; // opt.
    private String lastName;
    private String nickname;
    private String phonePrimary;
    private String phoneSecondary; // opt.
    private String email;
    private String linkedinUrl; // opt.
    private LocalDate birthDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructor (Tüm alanlar için)
    public Contact(int contactId, String firstName, String middleName, String lastName, String nickname, 
                   String phonePrimary, String phoneSecondary, String email, String linkedinUrl, 
                   LocalDate birthDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.contactId = contactId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.nickname = nickname;
        this.phonePrimary = phonePrimary;
        this.phoneSecondary = phoneSecondary;
        this.email = email;
        this.linkedinUrl = linkedinUrl;
        this.birthDate = birthDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // Getter Metotları
    public int getContactId() { return contactId; }
    public String getFirstName() { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getLastName() { return lastName; }
    public String getNickname() { return nickname; }
    public String getPhonePrimary() { return phonePrimary; }
    public String getPhoneSecondary() { return phoneSecondary; }
    public String getEmail() { return email; }
    public String getLinkedinUrl() { return linkedinUrl; }
    public LocalDate getBirthDate() { return birthDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // Setter Metotları (Güncelleme işlemleri için gereklidir, hepsini ekleyebilirsiniz)
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    // ... diğer setter'lar ...

    /**
     * Konsolda listeleme için formatlanmış çıktıyı döndürür.
     */
    @Override
    public String toString() {
        return String.format("| %-4d | %-15s | %-15s | %-15s | %-30s | %-10s |",
                contactId, firstName, lastName, phonePrimary, email, birthDate);
    }
}