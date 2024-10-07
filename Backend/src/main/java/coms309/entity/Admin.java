package coms309.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;

    @OneToOne
    @JoinColumn(name = "u_id", referencedColumnName = "user_id")
    private UserProfile userProfile;
}
