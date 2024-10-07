package coms309.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Date;


@Entity
@Getter
@Setter
@Table(name = "user_profiles")
public class UserProfile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    @Column(name = "contact_information", nullable = false)
    private String contactInformation;

    @Column(name = "job_title", nullable = false)
    private String jobTitle;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "date_of_hire", nullable = false)
    private Date dateOfHire;





}
