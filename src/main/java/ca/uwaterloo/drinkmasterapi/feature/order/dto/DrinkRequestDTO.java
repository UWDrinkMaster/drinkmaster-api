package ca.uwaterloo.drinkmasterapi.feature.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@Data
public class DrinkRequestDTO {
    @ApiModelProperty(value = "Name of the drink")
    private String name;

    @ApiModelProperty(value = "Image URL of the drink")
    private String imageUrl;

    @ApiModelProperty(value = "Category of the drink")
    private String category;

    @ApiModelProperty(value = "Description of the drink")
    private String description;

    @ApiModelProperty(value = "List of drink ingredients", required = true)
    @NotEmpty(message = "Drink ingredients list cannot be empty")
    private List<DrinkIngredientRequestDTO> drinkIngredients;
}
