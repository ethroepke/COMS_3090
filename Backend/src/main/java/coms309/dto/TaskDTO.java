package coms309.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TaskDTO {

    @NotBlank(message = "Tasks name is required")
    @Size(max = 100, message = "Tasks name must not exceed 100 characters")
    private String name;

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotBlank(message = "Status is required")
    private String status;  // e.g., "Assigned", "In Progress", "Completed"

    @Min(value = 0, message = "Progress must be at least 0%")
    @Max(value = 100, message = "Progress must not exceed 100%")
    private int progress;  // Represented as percentage (0-100)

    // Constructors
    public TaskDTO() {}

    public TaskDTO(String name, String description, String status, int progress) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.progress = progress;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
}
