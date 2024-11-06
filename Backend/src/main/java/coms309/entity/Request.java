package coms309.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "requests")
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long requestId;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "employer_id", nullable = false)
    private Employer employer;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Projects project;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestType status;

    @Column(name = "request_date", nullable = false)
    private Date requestDate;



}
