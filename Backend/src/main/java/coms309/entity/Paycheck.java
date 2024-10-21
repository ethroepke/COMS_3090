
package coms309.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "paychecks")
public class Paycheck implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paycheck_id")
    private Long paycheckId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfile userProfile;

    @Column(name = "hours_worked", nullable = false)
    private int hoursWorked;

    @Column(name = "pay_rate", nullable = false)
    private double payRate;

    @Column(name = "bonus", nullable = true)
    private double bonus;

    @Column(name = "deductibles", nullable = true)
    private double deductibles;

    @Column(name = "gross_pay", nullable = false)
    private double grossPay;

    @Column(name = "take_home_pay", nullable = false)
    private double takeHomePay;

    public Paycheck() {}

    public Paycheck(UserProfile userProfile, int hoursWorked, double payRate, double bonus, double deductibles) {
        this.userProfile = userProfile;
        this.hoursWorked = hoursWorked;
        this.payRate = payRate;
        this.bonus = bonus;
        this.deductibles = deductibles;
        this.grossPay = calculateGrossPay();
        this.takeHomePay = calculateTakeHomePay();
    }

    private double calculateGrossPay() {
        return (hoursWorked * payRate) + bonus;
    }

    private double calculateTakeHomePay() {
        return grossPay - deductibles;
    }
}
