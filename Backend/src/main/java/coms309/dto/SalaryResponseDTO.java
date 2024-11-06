package coms309.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class SalaryResponseDTO {
    private Long salaryId;
    private String username;
    private Double hoursWorked;
    private Double payRate;
    private Double bonusPay;
    private Double deductibles;
    private Double grossPay;
    private Double takeHomePay;
}
