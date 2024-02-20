package ca.uwaterloo.drinkmasterapi.feature.order.service;

import ca.uwaterloo.drinkmasterapi.feature.order.dto.DrinkRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.order.dto.DrinkResponseDTO;
import ca.uwaterloo.drinkmasterapi.feature.order.dto.IngredientResponseDTO;

import java.util.List;

public interface IDrinkService {
    List<IngredientResponseDTO> getAllIngredients();

    DrinkResponseDTO getDrinkById(Long drinkId);

    List<DrinkResponseDTO> getDrinkManuByUserId(Long userId);

    void createCustomizedDrink(DrinkRequestDTO drinkRequestDTO);
}
