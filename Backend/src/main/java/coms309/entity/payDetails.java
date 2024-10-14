package coms309.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Entity class representing the pay details of an employee.
 * 
 * Improvements:
 * - Added validation annotations to enforce data integrity.
 * - Enhanced field-level documentation.
 */
@Entity
@Getter
@Setter
@Table(name = "pay_details")
public class PayDetails implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_id")
    private Long id;

    @NotNull(message = "User profile cannot be null")
    @OneToOne
    @JoinColumn(name = "user_profile_id", referencedColumnName = "user_id", nullable = false)
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

    // Constructors
    public PayDetails() {}

    public PayDetails(UserProfile userProfile, Double hoursWorked, Double payRate, Double bonusPay, Double deductibles) {
        this.userProfile = userProfile;
        this.hoursWorked = hoursWorked;
        this.payRate = payRate;
        this.bonusPay = bonusPay != null ? bonusPay : 0.0; // Default to 0 if not provided
        this.deductibles = deductibles != null ? deductibles : 0.0; // Default to 0 if not provided
        
        // Calculate gross and take-home pay
        this.grossPay = calculateGrossPay();
        this.takeHomePay = calculateTakeHomePay();
    }

    private Double calculateGrossPay() {
        return (this.hoursWorked * this.payRate) + this.bonusPay;
    }

    private Double calculateTakeHomePay() {
        return this.grossPay - this.deductibles;
    }
}

