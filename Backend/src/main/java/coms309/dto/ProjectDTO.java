package coms309.dto;


import coms309.entity.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@Data
public class ProjectDTO {

    private Long projectId;

//    private String description;
//
//    private String projectName;
//
//    private String status;
//
//    private Date dueDate;
//
    private Date startDate;


    private Date endDate;

//    @NotNull(message = "Priority level is required")
//    private Priority priority;
//
//    @NotEmpty(message = "Employers list cannot be empty")
//    private List<@NotBlank(message = "Employer username cannot be blank") String> employerUsernames;


    private String projectName;
    private String description;
    private Date dueDate;
    private Priority priority;
//    private String employerUsername;
    private String status;
    private String employerUsername;

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
