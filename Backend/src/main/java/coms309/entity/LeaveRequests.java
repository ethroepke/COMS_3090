
package coms309.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Entity class representing leave requests made by employees.
 * 
 * Improvements:
 * - Added validation annotations to enforce data integrity.
 * - Enhanced field-level documentation.
 */
@Entity
@Getter
@Setter
@Table(name = "leave_requests")
public class LeaveRequests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_id")
    private Long leaveId;

    @NotNull(message = "Leave request date cannot be null")
    @Temporal(TemporalType.DATE)
    @Column(name = "leave_date")
    private Date leaveDate;

    @NotNull(message = "Leave duration cannot be null")
    @Column(name = "leave_duration")
    private Integer leaveDuration;

    @NotNull(message = "Employee cannot be null")
    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;
}
