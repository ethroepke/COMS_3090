package coms309.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Data
public class SalaryDTO {
    private double takeHomePay;
    private double grossPay;
    private double hoursWorked;
    private double payRate;
    private double bonusPay;
    private double deductibles;
    private String username;

}
