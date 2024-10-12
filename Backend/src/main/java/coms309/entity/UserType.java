
package coms309.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity class representing a user's type.
 * 
 * Improvements:
 * - Added validation annotations to enforce data integrity.
 * - Enhanced documentation for user role and relationships.
 */
@Entity
@Getter
@Setter
@Table(name = "user_type")
public class UserType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_type_id")
    private Long userTypeId;

    @NotNull(message = "Role cannot be null")
    @Column(name = "role")
    private String role;

    @OneToOne(mappedBy = "userType")
    private UserProfile userProfile;
}
