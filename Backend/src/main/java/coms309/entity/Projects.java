package coms309.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.User;

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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "projectId")
public class Projects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", nullable = false, updatable = false)
    private Long projectId;

    @NotBlank(message = "Projects name is required")
    @Size(max = 100, message = "Projects name must not exceed 100 characters")
    private String projectName;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    @NotNull(message = "Priority level is required")
    private Priority priority;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    @Column(name = "project_description", nullable = false)
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "Due_date")
    private Date dueDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "start_date", nullable = false)
    @NotNull(message = "Start date is required")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "end_date", nullable = false)
    @NotNull(message = "End date is required")
    private Date endDate;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Tasks> tasks = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "employer_project",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "employer_id")
    )
    @JsonBackReference
    private Set<Employer> employers = new HashSet<>();

    public void addEmployer(Employer employer) {
        this.employers.add(employer);
        employer.getProjects().add(this);
    }

    public void removeEmployer(Employer employer) {
        this.employers.remove(employer);
        employer.getProjects().remove(this);
    }


    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column(name = "status", nullable = false)

    private String status;


    public Projects(String description, Date dueDate, String projectName, String status, Priority priority, Date startDate, Date endDate) {
        this.projectName = projectName;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority=priority;
        this.endDate=endDate;
        this.startDate=startDate;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Projects() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }


    // Utility methods to manage bidirectional relationship with Tasks
    public void addTask(Tasks task) {
        this.tasks.add(task);
        task.setProjects(this);
    }

    public void removeTask(Tasks task) {
        this.tasks.remove(task);
        task.setProjects(null);
    }

    // Lifecycle hooks to update timestamps
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}