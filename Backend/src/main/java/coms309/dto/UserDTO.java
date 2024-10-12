package coms309.dto;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class UserDTO {
    private String password;

    private String username;

    @Nullable
    private String email;
}
