
package coms309.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Entity class representing an Admin.
 *
 * Improvements:
 * - Added validation annotations to enforce data integrity.
 * - Improved class-level and field-level documentation.
 */
@Data
@Entity
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;

    @NotNull(message = "UserProfile cannot be null")
    @OneToOne
    @JoinColumn(name = "u_id", referencedColumnName = "user_id")
    private UserProfile userProfile;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private UserType userType;
}
