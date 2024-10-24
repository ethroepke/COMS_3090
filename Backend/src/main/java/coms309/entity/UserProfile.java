
package coms309.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Entity class representing a user's profile.
 *
 * Improvements:
 * - Enhanced documentation for profile details.
 */
@Entity
@Getter
@Setter
@Table(name = "user_profiles")
public class UserProfile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "user_name", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = true)
    private UserType userType;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "job_title", nullable = true)
    private String jobTitle;

    @Column(name = "department", nullable = true)
    private String department;

    @Column(name = "date_of_hire", nullable = true)
    private Date dateOfHire;

    public UserProfile() {}

    public UserProfile(String name, String username, String email, String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.dateOfHire = new Date();
    }

    // Setter methods if needed explicitly
    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
