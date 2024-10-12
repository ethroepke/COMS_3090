package coms309.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

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

    @NotNull(message = "UserProfile cannot be null")
    @OneToOne
    @JoinColumn(name = "u_id", referencedColumnName = "user_id")
    private UserProfile userProfile;

    @NotNull(message = "Project assignment cannot be null")
    @ManyToOne
    @JoinColumn(name = "e_projects", referencedColumnName = "project_id")
    private Projects projects;

        @Enumerated(EnumType.STRING)
        @Column(name = "user_type")
        private UserType userType;
}
