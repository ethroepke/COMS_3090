package onetoone.Laptops;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import onetoone.Persons.Person;

/**
 *
 * @author Vivek Bengre
 */

@Entity
public class Laptop {

    /*
     * The annotation @ID marks the field below as the primary key for the table created by springboot
     * The @GeneratedValue generates a value if not already present, The strategy in this case is to start from 1 and increment for each table
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double cpuClock;
    private int cpuCores;
    private int ram;
    private String manufacturer;
    private int cost;

    /*
     * @OneToOne creates a relation between the current entity/table(Laptop) with the entity/table defined below it(Person)
     * @JsonIgnore is to assure that there is no infinite loop while returning either Person/laptop objects (laptop->Person->laptop->...)
     */
    @OneToOne
    @JsonIgnore
    private Person person;

    // Constructor chaining for better code reuse
    public Laptop( double cpuClock, int cpuCores, int ram, String manufacturer, int cost) {
        this(); // Call the no-arg constructor
        this.cpuClock = cpuClock;
        this.cpuCores = cpuCores;
        this.ram = ram;
        this.manufacturer = manufacturer;
        this.cost = cost;
    }

    public Laptop() {
        // No-arg constructor
    }

    // =============================== Getters and Setters for each field ================================== //

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public double getCpuClock(){
        return cpuClock;
    }

    public void setCpuClock(double cpuClock){
        this.cpuClock = cpuClock;
    }

    public int getCpuCores(){
        return cpuCores;
    }

    public void setCpuCores(int cpuCores){
        this.cpuCores = cpuCores;
    }

    public String getManufacturer(){
        return manufacturer;
    }

    public void setManufacturer(String manufacturer){
        this.manufacturer = manufacturer;
    }

    public int getCost(){
        return cost;
    }

    public void setCost(int cost){
        this.cost = cost;
    }

    public Person getPerson(){
        return person;
    }

    public void setPerson(Person person){
        this.person = person;
    }

    public int getRam(){
        return ram;
    }

    public void setRam(int ram){
        this.ram = ram;
    }

    // Override toString for better logging and debugging
    @Override
    public String toString() {
        return "Laptop [id=" + id + ", cpuClock=" + cpuClock + ", cpuCores=" + cpuCores +
                ", ram=" + ram + ", manufacturer=" + manufacturer + ", cost=" + cost + "]";
    }
}