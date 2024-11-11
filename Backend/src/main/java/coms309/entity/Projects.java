
package coms309.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

import java.time.LocalDate;
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
public class Projects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long projectId;


    @NotNull(message = "Project name cannot be null")
    @Column(name = "project_name", nullable = false)
    private String projectName;

    @NotNull(message = "Project description cannot be null")
    @Column(name = "project_description", nullable= false)
    private String Description;

    @Temporal(TemporalType.DATE)
    @Column(name = "Due_date")
    private Date dueDate ;


    @Column(name = "status", nullable = false)
    private String status;

    @ManyToMany(mappedBy = "projects")
    private Set<Employer> employers = new HashSet<>();

    @ManyToMany(mappedBy = "projects")
    private Set<Admin> admins = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private Set<Employee> employees = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Tasks> tasks = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    @NotNull(message = "Priority level is required")
    private Priority priority;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Start date is required")
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "End date is required")
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    public Projects(){}

    public Projects(Long projectId, String projectName, String Description, Date dueDate, String status, Priority priority, LocalDate startDate, LocalDate endDate ) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.Description= Description;
        this.dueDate = dueDate;
        this.status= status;
        this.priority = priority;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}