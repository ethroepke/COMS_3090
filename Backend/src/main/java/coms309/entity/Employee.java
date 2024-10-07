package coms309.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "employee")
public class Employee {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "employee_id")
        private Long employeeId;

        @OneToOne
        @JoinColumn (name = "u_id", referencedColumnName = "user_id")
        private UserProfile userProfile;

        @ManyToOne
        @JoinColumn(name = "e_projects" , referencedColumnName = "project_id")
        private Projects projects;



}
