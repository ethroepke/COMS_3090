package coms309.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name ="employer")
public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employer_id")
    private Long employerId;

    @OneToOne
    @JoinColumn (name = "u_id", referencedColumnName = "user_id")
    private UserProfile userProfile;

    @ManyToMany
    @JoinTable(name = "er_projects", joinColumns = @JoinColumn(
            name = "e_id",
            referencedColumnName = "employer_id"
    ),
            inverseJoinColumns = @JoinColumn(
                    name = "p_id",
                    referencedColumnName = "project_id"
            ))
    private List<Projects> projects;

    @ManyToOne
    @JoinColumn(name = "er_leaveRequests", referencedColumnName = "leave_id")
    private LeaveRequests leaveRequests;


    @ManyToOne
    @JoinColumn(name = "er_timeLog", referencedColumnName = "log_id")
    private TimeLog timeLog;

}
