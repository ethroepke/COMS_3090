package coms309.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Tasks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotBlank(message = "Tasks name is required")
    @Size(max = 100, message = "Tasks name must not exceed 100 characters")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotBlank(message = "Status is required")
    private String status;  // e.g., "Assigned", "In Progress", "Completed"

    @Min(value = 0, message = "Progress must be between 0 and 100")
    @Max(value = 100, message = "Progress must be between 0 and 100")
    private int progress;  // Represented as percentage (0-100)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", referencedColumnName = "project_id", nullable = false)
    private Projects project;

    @ManyToMany
    @JoinTable(
            name = "task_employee",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private Set<User> assignedEmployees = new HashSet<>();

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public Tasks() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Tasks(String name, String description, String status, int progress, Projects projects) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.progress = progress;
        this.project = projects;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public Projects getProjects() {
        return project;
    }

    public void setProjects(Projects projects) {
        this.project = projects;
    }

    public Set<User> getAssignedEmployees() {
        return assignedEmployees;
    }

    public void setAssignedEmployees(Set<User> assignedEmployees) {
        this.assignedEmployees = assignedEmployees;
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

    // Utility methods to manage bidirectional relationship with User
    public void addEmployee(User employee) {
        assignedEmployees.add(employee);
        employee.getTasks().add(this);
    }

    public void removeEmployee(User employee) {
        assignedEmployees.remove(employee);
        employee.getTasks().remove(this);
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