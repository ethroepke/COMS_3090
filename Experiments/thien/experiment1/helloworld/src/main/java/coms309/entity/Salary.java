package coms309.entity;

import jakarta.persistence.*;


@Entity
@Table(name= "salaries")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salary_id")
    private Long salaryId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

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

