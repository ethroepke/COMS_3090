package coms309.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String password;

    private String role; // Can be Admin, Employer, Employee, etc.

    private Date createdDate;
    private Long timeWorked;

    public User() {
        this.createdDate = new Date();
    }

    public User(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createdDate = new Date();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getTimeWorked() {
        return this.timeWorked; // Assuming `timeWorked` is a field in `User` entity
    }

    public void setTimeWorked(Long timeWorked) {
        // Ensure that timeWorked is within a valid range (e.g., 0 to 168 hours)
        if (timeWorked < 0 || timeWorked > 168) {
            throw new IllegalArgumentException("Time worked must be between 0 and 168 hours.");
        }

        // Set the timeWorked field
        this.timeWorked = timeWorked;
    }
}