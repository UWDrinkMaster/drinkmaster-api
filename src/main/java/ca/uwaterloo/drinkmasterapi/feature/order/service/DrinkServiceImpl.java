package ca.uwaterloo.drinkmasterapi.feature.order.service;

import ca.uwaterloo.drinkmasterapi.dao.Ingredient;
import ca.uwaterloo.drinkmasterapi.feature.order.dto.IngredientResponseDTO;
import ca.uwaterloo.drinkmasterapi.repository.DrinkRepository;
import ca.uwaterloo.drinkmasterapi.repository.IngredientRepository;
import ca.uwaterloo.drinkmasterapi.repository.MachineRepository;
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
    private final MachineRepository machineRepository;

    @Autowired
    public DrinkServiceImpl(UserRepository userRepository,
                            DrinkRepository drinkRepository,
                            IngredientRepository ingredientRepository,
                            MachineRepository machineRepository) {
        this.userRepository = userRepository;
        this.drinkRepository = drinkRepository;
        this.ingredientRepository = ingredientRepository;
        this.machineRepository = machineRepository;
    }

    @Override
    public List<IngredientResponseDTO> getAllIngredients() {
        List<Ingredient> ingredients = ingredientRepository.findAll();

        return ingredients.stream()
                .map(IngredientResponseDTO::new)
                .collect(Collectors.toList());
    }
}
