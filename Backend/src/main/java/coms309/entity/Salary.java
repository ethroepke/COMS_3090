
package coms309.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity class representing an employee's salary.
 * 
 * Improvements:
 * - Added validation annotations to enforce data integrity.
 * - Enhanced documentation for field-level relationships.
 */
@Entity
@Getter
@Setter
@Table(name = "salary")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salary_id")
    private Long salaryId;
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

    @NotNull(message = "Salary amount cannot be null")
    @Column(name = "salary_amount")
    private Double salaryAmount;

    @NotNull(message = "Employee cannot be null")
    @OneToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;
}
