package ca.uwaterloo.drinkmasterapi.feature.user.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class SignupRequestDTO {
    @NotBlank(message = "Email should not be blank")
    @Email(message = "Email is invalid")
    @ApiModelProperty(value = "Email address", example = "user@example.com", required = true)
    private String email;

    @NotBlank(message = "Password should not be blank")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).+$", message = "Password should have at least one letter and one number")
    @ApiModelProperty(value = "Password", example = "password123", required = true)
    private String password;
}
