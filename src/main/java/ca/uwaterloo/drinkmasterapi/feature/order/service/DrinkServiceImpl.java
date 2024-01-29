package ca.uwaterloo.drinkmasterapi.feature.order.service;

import ca.uwaterloo.drinkmasterapi.dao.*;
import ca.uwaterloo.drinkmasterapi.feature.order.dto.DrinkResponseDTO;
import ca.uwaterloo.drinkmasterapi.feature.order.dto.IngredientResponseDTO;
import ca.uwaterloo.drinkmasterapi.handler.exception.ResourceNotFoundException;
import ca.uwaterloo.drinkmasterapi.repository.DrinkRepository;
import ca.uwaterloo.drinkmasterapi.repository.IngredientRepository;
import ca.uwaterloo.drinkmasterapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DrinkServiceImpl implements IDrinkService {
    private final UserRepository userRepository;
    private final DrinkRepository drinkRepository;
    private final IngredientRepository ingredientRepository;

    @Autowired
    public DrinkServiceImpl(UserRepository userRepository,
                            DrinkRepository drinkRepository,
                            IngredientRepository ingredientRepository) {
        this.userRepository = userRepository;
        this.drinkRepository = drinkRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<IngredientResponseDTO> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();

        return ingredients.stream()
                .filter(Ingredient::getIsActive)
                .map(IngredientResponseDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<DrinkResponseDTO> getDrinkManuByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found."));

        List<Drink> allDrinks = drinkRepository.findByIsCustomizedFalseOrUserId(userId);

        return allDrinks.stream()
                .filter(Drink::getIsActive)
                .map(drink -> {
                    List<Long> drinkAllergenIds = getAllergensByDrink(drink);
                    boolean isAvailable = checkDrinkAvailability(drink);
                    boolean isAllergic = checkAllergens(user, drinkAllergenIds);

                    return new DrinkResponseDTO(drink, drinkAllergenIds, isAvailable, isAllergic);
                })
                .collect(Collectors.toList());
    }

    private List<Long> getAllergensByDrink(Drink drink) {
        return drink.getIngredients().stream()
                .flatMap(ingredient -> ingredient.getAllergens().stream())
                .map(Allergen::getId)
                .distinct()
                .collect(Collectors.toList());
    }

    private boolean checkDrinkAvailability(Drink drink) {
        List<DrinkIngredient> drinkIngredients = drink.getDrinkIngredients();

        for (DrinkIngredient drinkIngredient : drinkIngredients) {
            if (drinkIngredient.getIngredient().getInventory() < drinkIngredient.getQuantity()) {
                return false;
            }
        }
        return true;
    }

    private boolean checkAllergens(User user, List<Long> drinkAllergenIds) {
        List<Long> userAllergenIds = user.getAllergens().stream()
                .map(Allergen::getId).collect(Collectors.toList());

        return userAllergenIds.stream().anyMatch(drinkAllergenIds::contains);
    }
}
