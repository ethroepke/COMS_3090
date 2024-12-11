package coms309.entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Entity class representing a project.
 *
 * Improvements:
 * - Added validation annotations for data integrity.
 * - Enhanced documentation for relationships with employees and employers.
 */
@Entity
@Getter
@Setter
@Table(name = "projects")
public class Projects implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;


    @NotNull(message = "Project name cannot be null")
    @Column(name = "project_name", nullable = false)
    private String projectName;

    @NotNull(message = "Username cannot be null")
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull(message = "Project description cannot be null")
    @Column(name = "project_description", nullable= false)
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "Due_date")
    private Date dueDate ;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    @NotNull(message = "Priority level is required")
    private Priority priority;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employer_id", referencedColumnName = "employer_id", nullable = false)
    @JsonManagedReference
    private Employer employer;

    @ManyToMany(mappedBy = "projects")
    @JsonIgnore
    private Set<Admin> admins = new HashSet<>();

    @OneToMany(mappedBy = "projects", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("project-employee")
    private Set<Employee> employees = new HashSet<>();

    public Projects(){}

    public Projects(String description, Date dueDate, String projectName, String status ){
        this.description= description;
        this.projectName=projectName;
        this.status= status;
        this.dueDate= new Date();

    }



}

