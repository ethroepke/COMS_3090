
package coms309.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "salaryId")
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "salary_id")
    private Long salaryId;

    @NotNull(message = "User profile cannot be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_profile_id", referencedColumnName = "user_id", nullable = false)
    @OneToOne(mappedBy="salary" , cascade = CascadeType.ALL)
    @JsonIgnore
    private UserProfile userProfile;


    @NotNull(message = "Hours worked cannot be null")
    @Column(name = "hours_worked", nullable = false)
    private Double hoursWorked;

    @NotNull(message = "Pay rate cannot be null")
    @Column(name = "pay_rate", nullable = false)
    private Double payRate;

    @Column(name = "bonus_pay")
    private Double bonusPay = 0.0; // Default to 0 if not provided

    @Column(name = "deductibles")
    private Double deductibles = 0.0; // Default to 0 if not provided

    @Column(name = "gross_pay")
    private Double grossPay;

    @Column(name = "take_home_pay")
    private Double takeHomePay;

    public Salary(UserProfile userProfile, Double hoursWorked, Double payRate, Double bonusPay, Double deductibles) {
        this.userProfile = userProfile;
        this.hoursWorked = hoursWorked;
        this.payRate = payRate;
        this.bonusPay = bonusPay != null ? bonusPay : 0.0; // Default to 0 if not provided
        this.deductibles = deductibles != null ? deductibles : 0.0; // Default to 0 if not provided

        // Calculate gross and take-home pay
        this.grossPay = calculateGrossPay();
        this.takeHomePay = calculateTakeHomePay();
    }

    public Double calculateGrossPay() {
        return (this.hoursWorked * this.payRate) + this.bonusPay;
    }

    public Double calculateTakeHomePay() {
        return this.grossPay - this.deductibles;
    }
}
