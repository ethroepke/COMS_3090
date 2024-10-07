package coms309.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "task_assignments")
public class TaskAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Projects project;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "task_description", nullable = false)
    private String taskDescription;

    @Column(name = "priority", nullable = false)
    private String priority; // E.g., 'High', 'Medium', 'Low'

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "due_date", nullable = false)
    private Date dueDate;

    @Column(name = "completion_status", nullable = false)
    private String completionStatus; // E.g., 'Not Started', 'In Progress', 'Completed'


}

