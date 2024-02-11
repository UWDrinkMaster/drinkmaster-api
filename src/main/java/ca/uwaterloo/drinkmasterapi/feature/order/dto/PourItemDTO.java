package ca.uwaterloo.drinkmasterapi.feature.order.dto;

import ca.uwaterloo.drinkmasterapi.dao.DrinkIngredient;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PourItemDTO {
    private Integer drinkId;

    private Double amount;

    public PourItemDTO(DrinkIngredient drinkIngredient) {
        drinkId = drinkIngredient.getIngredient().getPosition();
        amount = drinkIngredient.getQuantity();
    }
}
