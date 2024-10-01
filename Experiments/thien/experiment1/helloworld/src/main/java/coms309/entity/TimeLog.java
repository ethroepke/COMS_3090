package coms309.entity;

import jakarta.persistence.*;

import java.util.Date;
@Entity
@Table(name= "time_logs")

public class TimeLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "date", nullable = false)
    private Date date;

    @Column(name = "clock_in_time", nullable = false)
    private Date clockInTime;

    @Column(name = "clock_out_time", nullable = false)
    private Date clockOutTime;

    @Column(name = "total_hours_worked", nullable = false)
    private Double totalHoursWorked;

    @Column(name = "overtime_hours")
    private Double overtimeHours;
}
