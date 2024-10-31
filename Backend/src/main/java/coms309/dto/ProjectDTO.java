package coms309.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
@Setter
@Getter
@Data
public class ProjectDTO {


    private String Description;

    private String projectName;

    private String status;


    private Date dueDate;





}
