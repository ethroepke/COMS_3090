package coms309.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class UserSignupDTO {
    private String password;

    private String username;

    private String email;
}
