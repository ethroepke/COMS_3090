package onetoone.Persons;

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
public class Person {

    /*
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String emailId;
    private boolean isActive;

    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(Person)
     * cascade is responsible propagating all changes, even to children of the class Eg: changes made to laptop within a Person object will be reflected
     * in the database (more info : https://www.baeldung.com/jpa-cascade-types)
     * @JoinColumn defines the ownership of the foreign key i.e. the Person table will have a field called laptop_id
     */
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "laptop_id")
    private Laptop laptop;

    // Constructor chaining for better reuse of code
    public Person(String name, String emailId) {
        this(name, emailId, true);  // Use constructor chaining
    }

    public Person(String name, String emailId, boolean isActive) {
        this.name = name;
        this.emailId = emailId;
        this.isActive = isActive;
    }

    public Person() {
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
        return "Person [id=" + id + ", name=" + name + ", emailId=" + emailId + ", isActive=" + isActive + "]";
    }
}