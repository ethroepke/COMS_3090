package coms309.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "employee")
public class Employee {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "employee_id")
        private Long employeeId;

        @Column(name = "name", nullable = false)
        private String employee_name;

        @Column(name = "contact_information", nullable = false)
        private String contactInformation;

        @Column(name = "job_title", nullable = false)
        private String jobTitle;

        @Column(name = "department", nullable = false)
        private String department;

        @Column(name = "date_of_hire", nullable = false)
        private Date dateOfHire;

        @Column(name = "supervisor", nullable = false)
        private String supervisor;

}
