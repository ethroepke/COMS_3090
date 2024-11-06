package coms309.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

   @OneToMany(mappedBy="employee", cascade = CascadeType.ALL)
   private List<LeaveRequests> leaveRequestsList = new ArrayList<>();

   @OneToMany(mappedBy = "employee" , cascade = CascadeType.ALL)
   private List<TimeLog> timeLogs= new ArrayList<>();

    @OneToOne(optional = false, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_profile_id", unique = true)
    private UserProfile userProfile;

    @NotNull(message = "Project assignment cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id", nullable = false)
    private Projects project;


    public Employee(){}
}
