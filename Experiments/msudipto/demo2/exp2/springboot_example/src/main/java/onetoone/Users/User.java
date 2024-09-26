package onetoone.Users;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import onetoone.Laptops.Laptop;

/**
 *
 * @author Vivek Bengre
 *
 */

@Entity
public class User {

    /*
     * The annotation @Id marks the field below as the primary key for the table created by Spring Boot.
     * The @GeneratedValue generates a value if not already present, starting from 1 and incrementing for each entry.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String emailId;
    private boolean isActive;

    /*
     * @OneToOne creates a relation between the current entity/table (User) with the entity/table defined below (Laptop).
     * CascadeType.ALL propagates changes (e.g., persist, update, delete) made to the user to its associated laptop.
     * @JoinColumn defines the ownership of the foreign key (laptop_id) in the user table.
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "laptop_id")
    private Laptop laptop;

    // Constructor chaining for better reuse of code
    public User(String name, String emailId) {
        this(name, emailId, true);  // Defaulting isActive to true
    }

    public User(String name, String emailId, boolean isActive) {
        this.name = name;
        this.emailId = emailId;
        this.isActive = isActive;
    }

    public User() {
    }

    // =============================== Getters and Setters for each field ================================== //

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) {  // Null safety check
            this.name = name;
        }
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        if (emailId != null) {  // Null safety check
            this.emailId = emailId;
        }
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Laptop getLaptop() {
        return laptop;
    }

    public void setLaptop(Laptop laptop) {
        this.laptop = laptop;
    }

    // Override toString for better logging and debugging
    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", emailId=" + emailId + ", isActive=" + isActive + "]";
    }
}