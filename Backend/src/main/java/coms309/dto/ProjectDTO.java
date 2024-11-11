package coms309.dto;


import coms309.entity.Priority;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@Data
public class ProjectDTO {

    private Long projectId;

    private String Description;

    private String projectName;

    private String status;

    private Date dueDate;

    public List<String> getEmployerUsernames() {
        return getEmployerUsernames();
    }

    public @NotNull(message = "Start date is required") LocalDate getStartDate() {
        return getStartDate();
    }

    public @NotNull(message = "End date is required") LocalDate getEndDate() {
        return getEndDate();
    }

    public @NotNull(message = "Priority level is required") Priority getPriority() {
        return getPriority();
    }
}
