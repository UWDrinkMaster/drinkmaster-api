package ca.uwaterloo.drinkmasterapi.feature.user.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Data
public class LoginRequestDTO {
    @NotBlank(message = "Email should not be blank")
    @Email(message = "Email is invalid")
    private String email;

    @NotBlank(message = "Password should not be blank")
    private String password;
}
