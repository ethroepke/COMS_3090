
package coms309.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
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
    private Set<Employee> employees = new HashSet<>();

    public Projects(){}

    public Projects(String Description, Date dueDate, String projectName, String status ){
        this.Description= Description;
        this.projectName=projectName;
        this.status= status;
        this.dueDate= new Date();

    }




}
