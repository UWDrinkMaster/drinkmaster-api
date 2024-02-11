package ca.uwaterloo.drinkmasterapi.feature.order.service;

import ca.uwaterloo.drinkmasterapi.dao.DrinkIngredient;
import ca.uwaterloo.drinkmasterapi.dao.Ingredient;
import ca.uwaterloo.drinkmasterapi.feature.order.dto.PourItemDTO;
import ca.uwaterloo.drinkmasterapi.handler.exception.*;
import ca.uwaterloo.drinkmasterapi.dao.Drink;
import ca.uwaterloo.drinkmasterapi.repository.*;
import ca.uwaterloo.drinkmasterapi.dao.Order;
import ca.uwaterloo.drinkmasterapi.feature.order.dto.OrderRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.order.dto.OrderResponseDTO;
import ca.uwaterloo.drinkmasterapi.common.OrderStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final DrinkRepository drinkRepository;
    private final IngredientRepository ingredientRepository;
    private final DrinkIngredientRepository drinkIngredientRepository;
    private final IMqttClientServiceImpl mqttClientService;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            DrinkRepository drinkRepository,
                            IngredientRepository ingredientRepository,
                            DrinkIngredientRepository drinkIngredientRepository,
                            IMqttClientServiceImpl mqttClientService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.drinkRepository = drinkRepository;
        this.ingredientRepository = ingredientRepository;
        this.drinkIngredientRepository = drinkIngredientRepository;
        this.mqttClientService = mqttClientService;
    }

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequest) {
        // Check if userId, drinkId exist
        userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + orderRequest.getUserId() + " not found."));
        Drink drink = drinkRepository.findById(orderRequest.getDrinkId())
                .orElseThrow(() -> new ResourceNotFoundException ("Drink with ID " + orderRequest.getDrinkId() + " not found."));

        List<DrinkIngredient> drinkIngredients = drinkIngredientRepository.findByDrinkId(drink.getId());
        updateIngredientInventory(drinkIngredients);

        // Create the order entity and set the attributes
        Order order = new Order();
        LocalDateTime orderPlacementTime = LocalDateTime.now().withNano(0);

        order.setMachineId(1L);
        order.setUserId(orderRequest.getUserId());
        order.setDrinkId(orderRequest.getDrinkId());
        order.setQuantity(1);
        order.setPrice(drink.getPrice());
        order.setPriceCurrency(drink.getPriceCurrency());
        order.setStatus(OrderStatusEnum.CREATED);
        order.setCreatedAt(orderPlacementTime);
        order.setModifiedAt(orderPlacementTime);

        Order savedOrder = orderRepository.save(order);

        try {
            List<PourItemDTO> pourItems = drinkIngredients.stream()
                    .map(PourItemDTO::new)
                    .collect(Collectors.toList());

            mqttClientService.publishPourMessage(savedOrder.getId(), 1L, savedOrder.getId(), orderPlacementTime, pourItems);
        } catch (Exception e) {
            throw new OrderFailedException("Order failed due to some internal error, please contact administrator.");
        }

        return new OrderResponseDTO(savedOrder);
    }

    @Override
    public List<OrderResponseDTO> getOrderByUserId(Long userId) {
        // Check if userId exist
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found."));

        // Retrieve all orders belonging to the specified userId
        List<Order> userOrders = orderRepository.findByUserId(userId);

        // Convert Order entities to OrderResponseDTOs and return the list
        return userOrders.stream()
                .map(OrderResponseDTO::new)
                .collect(Collectors.toList());
    }

    private void updateIngredientInventory(List<DrinkIngredient> drinkIngredients) throws InvalidCredentialsException {
        List<Ingredient> ingredients = new ArrayList<>();
        for (DrinkIngredient drinkIngredient : drinkIngredients) {
            Ingredient ingredient = drinkIngredient.getIngredient();
            Double requiredQuantity = drinkIngredient.getQuantity();

            if (ingredient.getInventory() < requiredQuantity) {
                throw new InventoryShortageException("Insufficient inventory for ingredient: " + ingredient.getName());
            }

            ingredient.setInventory(ingredient.getInventory() - requiredQuantity);
            ingredients.add(ingredient);
        }
        ingredientRepository.saveAll(ingredients);
    }
}
