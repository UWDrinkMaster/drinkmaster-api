package ca.uwaterloo.drinkmasterapi.feature.user.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginRequestDTO {
    @NotBlank(message = "Email should not be blank")
    @Email(message = "Email is invalid")
    @ApiModelProperty(value = "Email address", example = "user@example.com", required = true)
    private String email;

    @NotBlank(message = "Password should not be blank")
    @ApiModelProperty(value = "Password", example = "password123", required = true)
    private String password;
}
