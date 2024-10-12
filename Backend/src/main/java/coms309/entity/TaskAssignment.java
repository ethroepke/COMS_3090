package coms309.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Entity class representing a task assigned to an employee.
 *
 * Improvements:
 * - Added validation annotations to enforce data integrity.
 * - Improved documentation for task details and relationships.
 */
@Entity
@Getter
@Setter
@Table(name = "task_assignment")
public class TaskAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;

    @NotNull(message = "Task description cannot be null")
    @Column(name = "task_description")
    private String taskDescription;

    @Column(name = "priority", nullable = false)
    private String priority; // E.g., 'High', 'Medium', 'Low'


    @Column(name = "due_date", nullable = false)
    private Date dueDate;

    @Column(name = "completion_status", nullable = false)
    private String completionStatus; // E.g., 'Not Started', 'In Progress', 'Completed'
}

