
package coms309.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "userId")
public class UserProfile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name= "full_name", nullable = false)
    private String fullName;

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


    @Column(name= "time_worked", nullable = true)
    private int timeWorked;

    @Column(name = "next_shift", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date nextShift;

    @NotNull(message = "Salary cannot be null")
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "salary_id", referencedColumnName = "salary_id", nullable = false)
    @JsonManagedReference
    private Salary salary;

    @OneToOne(mappedBy = "userProfile", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Employer employer;

    @ManyToMany(mappedBy = "users")
    private List<GroupChat> groupChats;

    public UserProfile(Long userId, String password, String username, String email ) {
        this.userId = Long.valueOf(userId);
        this.password = password;
        this.username = username;
        this.email = email;
        this.dateOfHire = new Date();
        this.timeWorked=0;
        this.nextShift= new Date();
    }


    public UserProfile(){}
}
