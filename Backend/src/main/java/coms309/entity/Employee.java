package coms309.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity class representing an Employee.
 */
@Entity
@Getter
@Setter
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    @OneToMany(mappedBy="employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<LeaveRequests> leaveRequestsList = new ArrayList<>();

    @OneToMany(mappedBy = "employee" , cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<TimeLog> timeLogs = new ArrayList<>();

    @OneToOne(optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_profile_id", unique = true)
    @JsonManagedReference
    private UserProfile userProfile;

    @NotNull(message = "Project assignment cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id", nullable = false)
    @JsonManagedReference
    private Projects project;

    public Employee(){}
}

