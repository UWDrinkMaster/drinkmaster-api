package ca.uwaterloo.drinkmasterapi.feature.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@Data
public class DrinkIngredientRequestDTO {
    @ApiModelProperty(value = "ID of the ingredient")
    @NotNull(message = "Ingredient ID cannot be null")
    private Long ingredientId;

    @ApiModelProperty(value = "Quantity of the ingredient in the drink")
    @NotNull(message = "Quantity cannot be null")
    @Positive(message = "Quantity must be positive for ingredients")
    private Double quantity;
}