
package coms309.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Entity class representing a time log entry for an employee.
 * 
 * Improvements:
 * - Added validation annotations to enforce data integrity.
 * - Enhanced documentation for field-level relationships and time tracking.
 */
@Entity
@Getter
@Setter
@Table(name = "time_log")
public class TimeLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "timeLog_id")
    private Long timeLogId;

    @NotNull(message = "Log date cannot be null")
    @Temporal(TemporalType.DATE)
    @Column(name = "log_date")
    private Date logDate;

    @NotNull(message = "Hours logged cannot be null")
    @Column(name = "hours_logged")
    private Integer hoursLogged;

    @NotNull(message = "Employee cannot be null")
    @ManyToOne
    @JoinColumn(name = "timeTrackingForEmployee", referencedColumnName = "employee_id")
    private Employee employee;


}
