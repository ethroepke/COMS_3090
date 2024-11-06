package coms309.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Projects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", nullable = false, updatable = false)
    private Long projectId;

    @NotBlank(message = "Projects name is required")
    @Size(max = 100, message = "Projects name must not exceed 100 characters")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(name = "Due_date")
    private Date dueDate;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tasks> tasks = new ArrayList<>();

    @ManyToMany(mappedBy = "projects")
    private Set<Employer> employers = new HashSet<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Column(name = "status", nullable = false)
    // Additional fields based on the constructor
    private String status;

    // Constructors
    public Projects(String description, Date dueDate, String name, String status) {
        this.name = name;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Projects() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(List<Tasks> tasks) {
        this.tasks = tasks;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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