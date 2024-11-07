package coms309.dto;


import coms309.entity.Priority;
import coms309.entity.UserProfile;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.List;

@Setter
@Getter
@Data
public class ProjectDTO {

    @NotBlank(message = "Description is required")
    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    @NotBlank(message = "Project name is required")
    @Size(max = 100, message = "Project name must not exceed 100 characters")
    private String projectName;

    @NotBlank(message = "Status is required")
    private String status;

    @NotNull(message = "Due date is required")
    private Date dueDate;

    
    private Date startDate;

    
    private Date endDate;


    @NotNull(message = "Priority level is required")
    private Priority priority;

    @NotEmpty(message = "Employers list cannot be empty")
    private List<@NotBlank(message = "Employer username cannot be blank") String> employerUsernames;
}
