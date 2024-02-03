package ca.uwaterloo.drinkmasterapi.feature.order.service;

import ca.uwaterloo.drinkmasterapi.common.CurrencyEnum;
import ca.uwaterloo.drinkmasterapi.dao.*;
import ca.uwaterloo.drinkmasterapi.feature.order.dto.DrinkIngredientRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.order.dto.DrinkRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.order.dto.DrinkResponseDTO;
import ca.uwaterloo.drinkmasterapi.feature.order.dto.IngredientResponseDTO;
import ca.uwaterloo.drinkmasterapi.handler.exception.ResourceNotFoundException;
import ca.uwaterloo.drinkmasterapi.repository.DrinkIngredientRepository;
import ca.uwaterloo.drinkmasterapi.repository.DrinkRepository;
import ca.uwaterloo.drinkmasterapi.repository.IngredientRepository;
import ca.uwaterloo.drinkmasterapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DrinkServiceImpl implements IDrinkService {
    private final UserRepository userRepository;
    private final DrinkRepository drinkRepository;
    private final IngredientRepository ingredientRepository;
    private final DrinkIngredientRepository drinkIngredientRepository;

    @Autowired
    public DrinkServiceImpl(UserRepository userRepository,
                            DrinkRepository drinkRepository,
                            IngredientRepository ingredientRepository,
                            DrinkIngredientRepository drinkIngredientRepository) {
        this.userRepository = userRepository;
        this.drinkRepository = drinkRepository;
        this.ingredientRepository = ingredientRepository;
        this.drinkIngredientRepository = drinkIngredientRepository;
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

    @Override
    public void createCustomizedDrink(DrinkRequestDTO drinkRequestDTO) {
        userRepository.findById(drinkRequestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + drinkRequestDTO.getUserId() + " not found."));

        LocalDateTime currentTime = LocalDateTime.now().withNano(0);
        List<DrinkIngredient> drinkIngredients = new ArrayList<>();
        Set<Long> ingredientIdsSet = new HashSet<>();
        for (DrinkIngredientRequestDTO drinkIngredientRequestDTO: drinkRequestDTO.getDrinkIngredients()) {
            Ingredient ingredient = ingredientRepository.findById(drinkIngredientRequestDTO.getIngredientId())
                    .orElseThrow(() -> new ResourceNotFoundException("Ingredient with ID " + drinkIngredientRequestDTO.getIngredientId() + " not found."));
            if (!ingredientIdsSet.add(drinkIngredientRequestDTO.getIngredientId())) {
                throw new ResourceNotFoundException("Duplicate ingredient ID found: " + drinkIngredientRequestDTO.getIngredientId());
            }
            DrinkIngredient drinkIngredient = new DrinkIngredient();
            drinkIngredient.setIngredient(ingredient);
            drinkIngredient.setQuantity(drinkIngredientRequestDTO.getQuantity());
            drinkIngredient.setCreatedAt(currentTime);
            drinkIngredient.setModifiedAt(currentTime);
            drinkIngredients.add(drinkIngredient);
        }

        Drink drink = new Drink();
        drink.setName(drinkRequestDTO.getName() == null ? "My customized drink" : drinkRequestDTO.getName());
        drink.setImageUrl(drinkRequestDTO.getImageUrl());
        drink.setPrice(9.99);
        drink.setPriceCurrency(CurrencyEnum.CAD);
        drink.setCategory(drinkRequestDTO.getCategory());
        drink.setDescription(drinkRequestDTO.getDescription());
        drink.setIsActive(true);
        drink.setIsCustomized(true);
        drink.setUserId(drinkRequestDTO.getUserId());
        drink.setMachineId(1L); // hard-coded
        drink.setCreatedAt(currentTime);
        drink.setModifiedAt(currentTime);

        Drink savedDrink = drinkRepository.save(drink);

        for (DrinkIngredient drinkIngredient : drinkIngredients) {
            drinkIngredient.setDrink(savedDrink);
        }
        drinkIngredientRepository.saveAll(drinkIngredients);
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
