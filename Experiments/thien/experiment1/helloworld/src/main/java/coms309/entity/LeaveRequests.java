package coms309.entity;
import jakarta.persistence.*;


import java.util.Date;

@Entity
@Table(name = "leave_requests")
public class LeaveRequests {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "leave_id")
    private Long leaveId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(name = "type_of_leave", nullable = false)
    private String typeOfLeave; // E.g., 'Vacation', 'Sick', 'Personal'

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "approval_status", nullable = false)
    private String approvalStatus; // E.g., 'Approved', 'Pending', 'Rejected'

    @Column(name = "remarks_notes")
    private String remarksNotes;

    // Getters and Setters
}

