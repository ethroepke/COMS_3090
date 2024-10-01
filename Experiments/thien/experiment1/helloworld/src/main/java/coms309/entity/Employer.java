package coms309.entity;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name ="employer")

public class Employer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employer_id")
    private Long employerId;

    @Column(name = "name", nullable = false)
    private String employer_name;

    @Column(name = "contact_information", nullable = false)
    private String contactInformation;

    @Column(name = "job_title", nullable = false)
    private String jobTitle;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "date_of_hire", nullable = false)
    private Date dateOfHire;

    @Column(name = "supervisor")
    private String supervisor;
}
