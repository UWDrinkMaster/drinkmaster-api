package ca.uwaterloo.drinkmasterapi.feature.user.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class SignupRequestDTO {
    @NotBlank(message = "Email should not be blank")
    @Email(message = "Email is invalid")
    private String email;

    @NotBlank(message = "Password should not be blank")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "Password should have at least one letter and one number")
    private String password;
}
