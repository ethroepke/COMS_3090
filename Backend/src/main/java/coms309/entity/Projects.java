
package coms309.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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
    @Column(name = "project_name")
    private String projectName;

    @NotNull(message = "Project description cannot be null")
    @Column(name = "project_description")
    private String projectDescription;

    @ManyToMany(mappedBy = "projects")
    private List<Employer> employers;

    @OneToMany(mappedBy = "projects")
    private List<Employee> employees;
}
