package ca.uwaterloo.drinkmasterapi.feature.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Data
public class UserAllergenRequestDTO {
    @ApiModelProperty(value = "ID of the user", example = "1", required = true)
    @NotNull(message = "User ID cannot be blank")
    private Long userId;

    @ApiModelProperty(value = "IDs of the allergens", required = true)
    @NotEmpty(message = "Allergen IDs cannot be blank")
    private List<Long> allergenIds;
}
