package coms309.dto;

import coms309.dto.*;
import coms309.entity.*;
import coms309.entity.*;
import coms309.exception.*;
import coms309.repository.*;
import coms309.service.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class ScheduleDTO {

    @NotBlank(message = "Event type is required")
    @Size(max = 50, message = "Event type must not exceed 50 characters")
    private String eventType; // e.g., "Shift", "Meeting"

    @NotNull(message = "Start time is required")
    @Future(message = "Start time must be in the future")
    private LocalDateTime startTime;

    @NotNull(message = "End time is required")
    @Future(message = "End time must be in the future")
    private LocalDateTime endTime;

    @NotNull(message = "User ID is required")
    private Long userId; // ID of the user assigned to this schedule

    @NotNull(message = "Project ID is required") // Added for project association
    private Long projectId; // ID of the project associated with this schedule

    @NotNull
    private UserDTO user;

    // Constructors
    public ScheduleDTO() {}

    public ScheduleDTO(String eventType, LocalDateTime startTime, LocalDateTime endTime, Long userId, Long projectId) {
        this.eventType = eventType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userId = userId;
        this.projectId = projectId;
    }

    // Getters and Setters
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}