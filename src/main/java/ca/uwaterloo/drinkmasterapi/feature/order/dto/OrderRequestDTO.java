package ca.uwaterloo.drinkmasterapi.feature.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
public class OrderRequestDTO {
    @ApiModelProperty(value = "ID of the user", example = "1", required = true)
    @NotNull(message = "User ID cannot be blank")
    private Long userId;

    @ApiModelProperty(value = "ID of the drink", example = "1", required = true)
    @NotNull(message = "Drink ID cannot be blank")
    private Long drinkId;
}
