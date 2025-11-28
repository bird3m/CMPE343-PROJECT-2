package dao;

import model.Contact;
import utils.DatabaseConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for the 'contacts' table. Rehber kayıtları üzerinde tüm işlemleri yönetir.
 */
public class ContactDAO {

    /**
     * Tüm kontakları veritabanından çeker. Tester yetkisi.
     * @return Contact nesnelerinden oluşan liste.
     */
    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();
        // Proje Gereksinimi: contact_id, first_name, last_name, phone_primary, email, birth_date
        String sql = "SELECT * FROM contacts ORDER BY contact_id ASC";
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                contacts.add(mapResultSetToContact(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error listing contacts: " + e.getMessage());
        }
        return contacts;
    }
    
    /**
     * Tek alana göre arama yapar (Örn: Ada Göre Arama). Tester yetkisi.
     */
    public List<Contact> searchContacts(String fieldName, String keyword) {
        List<Contact> contacts = new ArrayList<>();
        // Güvenlik için LIKE kullanımı
        String sql = "SELECT * FROM contacts WHERE " + fieldName + " LIKE ?";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            // Kısmi (Substring) arama için % kullanımı
            pstmt.setString(1, "%" + keyword + "%"); 
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                contacts.add(mapResultSetToContact(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error searching contacts by single field: " + e.getMessage());
        }
        return contacts;
    }
    
    /**
     * Çoklu alana göre arama yapar. Tester yetkisi.
     * Not: Bu metot, dinamik SQL sorgusu oluşturacak karmaşık bir mantık gerektirir.
     */
    public List<Contact> searchContactsMulti(String dynamicWhereClause) {
        // Bu metodun doğru çalışması için, ui veya service katmanında WHERE kısmını inşa etmelisiniz.
        System.out.println("Executing complex search with WHERE: " + dynamicWhereClause);
        // Örnek: SELECT * FROM contacts WHERE first_name LIKE 'Ahmet%' AND MONTH(birth_date) = 11
        
        // SQL Injection riskini azaltmak için PreparedStatement kullanımı önerilir.
        return new ArrayList<>(); 
    }
    
    /**
     * Tüm sonuçları seçilen alana göre sıralar. Tester yetkisi.
     */
    public List<Contact> sortContacts(String field, String order) {
        List<Contact> contacts = new ArrayList<>();
        // ORDER parametresini güvenli hale getirmek kritik
        String safeOrder = order.equalsIgnoreCase("DESC") ? "DESC" : "ASC";
        String sql = "SELECT * FROM contacts ORDER BY " + field + " " + safeOrder;
        
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                contacts.add(mapResultSetToContact(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error sorting contacts: " + e.getMessage());
        }
        return contacts;
    }
    
    // --- Yardımcı Metot ---
    
    /**
     * ResultSet'teki verileri Contact nesnesine eşler.
     */
    private Contact mapResultSetToContact(ResultSet rs) throws SQLException {
        // SQL'den gelen TIMESTAMP'i LocalDateTime'a dönüştür
        Timestamp created = rs.getTimestamp("created_at");
        Timestamp updated = rs.getTimestamp("updated_at");

        return new Contact(
            rs.getInt("contact_id"),
            rs.getString("first_name"),
            rs.getString("middle_name"),
            rs.getString("last_name"),
            rs.getString("nickname"),
            rs.getString("phone_primary"),
            rs.getString("phone_secondary"),
            rs.getString("email"),
            rs.getString("linkedin_url"),
            rs.getDate("birth_date") != null ? rs.getDate("birth_date").toLocalDate() : null,
            created != null ? created.toLocalDateTime() : null,
            updated != null ? updated.toLocalDateTime() : null
        );
    }
}