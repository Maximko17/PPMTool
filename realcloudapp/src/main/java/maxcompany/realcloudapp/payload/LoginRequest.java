package maxcompany.realcloudapp.payload;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "Password is required")
    private String password;



}
