package ca.uwaterloo.drinkmasterapi.feature.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Data
public class OrderRequestDTO {
    @ApiModelProperty(value = "ID of the machine", example = "1", required = true)
    @NotNull(message = "Machine ID cannot be blank")
    private Long machineId;

    @ApiModelProperty(value = "ID of the user", example = "1", required = true)
    @NotNull(message = "User ID cannot be blank")
    private Long userId;

    @ApiModelProperty(value = "ID of the drink", example = "1", required = true)
    @NotNull(message = "Drink ID cannot be blank")
    private Long drinkId;

    @ApiModelProperty(value = "Quantity of the drink", example = "2", required = true)
    @Min(value = 1, message = "Quantity must be at least one")
    private int quantity;
}
