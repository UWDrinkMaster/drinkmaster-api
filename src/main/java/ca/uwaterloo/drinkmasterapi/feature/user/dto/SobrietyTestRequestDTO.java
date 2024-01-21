package ca.uwaterloo.drinkmasterapi.feature.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
public class SobrietyTestRequestDTO {
    @ApiModelProperty(value = "ID of the user", example = "1", required = true)
    @NotNull(message = "User ID cannot be blank")
    private Long userId;

    @ApiModelProperty(value = "Score of the latest sobriety test", example = "0.98", required = true)
    @Min(value = 0, message = "Score must be non-negative")
    private double score;
}
