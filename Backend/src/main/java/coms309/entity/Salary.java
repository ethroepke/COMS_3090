package coms309.entity;

import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name= "salaries")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salary_id")
    private Long salaryId;

    @OneToOne
    @JoinColumn(name = "eID_salary", referencedColumnName = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "erID_salary", referencedColumnName = "employer_id")
    private Employer employers;

    @Column(name = "base_salary", nullable = false)
    private Double baseSalary;

    @Column(name = "bonus")
    private Double bonus;

    @Column(name = "deductions")
    private Double deductions;

    @Column(name = "pay_period", nullable = false)
    private String payPeriod; // E.g., 'Monthly', 'Bi-weekly'

    @Column(name = "total_compensation", nullable = false)
    private Double totalCompensation;


}

