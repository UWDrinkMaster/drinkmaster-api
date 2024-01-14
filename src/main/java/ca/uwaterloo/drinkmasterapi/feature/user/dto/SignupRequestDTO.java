package ca.uwaterloo.drinkmasterapi.feature.user.dto;

import javax.validation.constraints.*;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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

    @NotNull(message = "Date of birth should not be null")
    @Past(message = "Date of birth should be in the past")
    @ApiModelProperty(value = "Date of birth", example = "1990-01-01", required = true)
    private LocalDate dateOfBirth;
}
