
package coms309.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Entity class representing an Employer.
 * 
 * Improvements:
 * - Added validation annotations to enforce data integrity.
 * - Enhanced documentation for field-level relationships.
 */
@Entity
@Getter
@Setter
@Table(name = "employer")
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employer_id")
    private Long employerId;

    @NotNull(message = "UserProfile cannot be null")
    @OneToOne
    @JoinColumn(name = "u_id", referencedColumnName = "user_id")
    private UserProfile userProfile;

    @NotNull(message = "Projects list cannot be null")
    @ManyToMany
    @JoinTable(
            name = "er_projects",
            joinColumns = @JoinColumn(name = "e_id", referencedColumnName = "employer_id"),
            inverseJoinColumns = @JoinColumn(name = "p_id", referencedColumnName = "project_id")
    )
    private List<Projects> projects;

    @NotNull(message = "Leave Requests cannot be null")
    @ManyToOne
    @JoinColumn(name = "er_leaveRequests", referencedColumnName = "leave_id")
    private LeaveRequests leaveRequests;
}
