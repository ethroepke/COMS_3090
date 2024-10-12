
package onetoone.Laptops;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import onetoone.Persons.Person;

/**
 * Entity representing a Laptop
 */
@Entity
public class Laptop {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double cpuClock;
    private int cpuCores;
    private int ram;
    private String manufacturer;
    private int price;  // renamed 'cost' to 'price' for clarity

    @OneToOne
    @JsonIgnore
    private Person person; // renamed 'Person' to 'person' to follow naming conventions

    // Constructor
    public Laptop(double cpuClock, int cpuCores, int ram, String manufacturer, int price) {
        this.cpuClock = cpuClock;
        this.cpuCores = cpuCores;
        this.ram = ram;
        this.manufacturer = manufacturer;
        this.price = price;
    }

    // Default constructor
    public Laptop() {}

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCpuClock() {
        return cpuClock;
    }

    public void setCpuClock(double cpuClock) {
        this.cpuClock = cpuClock;
    }

    public int getCpuCores() {
        return cpuCores;
    }

    public void setCpuCores(int cpuCores) {
        this.cpuCores = cpuCores;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
