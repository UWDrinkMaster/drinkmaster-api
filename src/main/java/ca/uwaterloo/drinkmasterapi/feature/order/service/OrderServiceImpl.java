package ca.uwaterloo.drinkmasterapi.feature.order.service;

import ca.uwaterloo.drinkmasterapi.dao.DrinkIngredient;
import ca.uwaterloo.drinkmasterapi.dao.Ingredient;
import ca.uwaterloo.drinkmasterapi.feature.order.dto.PourItemDTO;
import ca.uwaterloo.drinkmasterapi.feature.order.event.OrderCompletionEvent;
import ca.uwaterloo.drinkmasterapi.feature.order.event.PourDrinkEvent;
import ca.uwaterloo.drinkmasterapi.handler.exception.*;
import ca.uwaterloo.drinkmasterapi.dao.Drink;
import ca.uwaterloo.drinkmasterapi.repository.*;
import ca.uwaterloo.drinkmasterapi.dao.Order;
import ca.uwaterloo.drinkmasterapi.feature.order.dto.OrderRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.order.dto.OrderResponseDTO;
import ca.uwaterloo.drinkmasterapi.common.OrderStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final ApplicationEventPublisher eventPublisher;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            DrinkRepository drinkRepository,
                            IngredientRepository ingredientRepository,
                            DrinkIngredientRepository drinkIngredientRepository,
                            ApplicationEventPublisher eventPublisher,
                            SimpMessagingTemplate messagingTemplate) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.drinkRepository = drinkRepository;
        this.ingredientRepository = ingredientRepository;
        this.drinkIngredientRepository = drinkIngredientRepository;
        this.eventPublisher = eventPublisher;
        this.messagingTemplate = messagingTemplate;
    }

    @EventListener
    public void onOrderCompletion(OrderCompletionEvent event) {
        updateOrderStatus(event.getOrderId(), event.isCompleted());
    }

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequest) {
        // Check if userId, drinkId exist
        userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + orderRequest.getUserId() + " not found."));
        Drink drink = drinkRepository.findById(orderRequest.getDrinkId())
                .orElseThrow(() -> new ResourceNotFoundException ("Drink with ID " + orderRequest.getDrinkId() + " not found."));

        List<DrinkIngredient> drinkIngredients = drinkIngredientRepository.findByDrinkId(drink.getId());
        List<Ingredient> ingredients = updateIngredientInventory(drinkIngredients);

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
            PourDrinkEvent pourMessageEvent = new PourDrinkEvent(this,
                    savedOrder.getId(),
                    1L,
                    savedOrder.getId(),
                    orderPlacementTime,
                    pourItems);
            eventPublisher.publishEvent(pourMessageEvent);
        } catch (Exception e) {
            order.setStatus(OrderStatusEnum.CANCELED);
            order.setModifiedAt(LocalDateTime.now().withNano(0));
            orderRepository.save(order);
            throw new OrderFailedException("Order failed due to some internal error, please contact administrator.");
        }

        ingredientRepository.saveAll(ingredients);
        order.setStatus(OrderStatusEnum.PENDING);
        order.setModifiedAt(LocalDateTime.now().withNano(0));
        Order pendingOrder = orderRepository.save(order);
        return new OrderResponseDTO(pendingOrder);
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

    @Override
    public void updateOrderStatus(Long orderId, boolean isCompleted) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
        order.setStatus(isCompleted ? OrderStatusEnum.COMPLETED : OrderStatusEnum.CANCELED);
        order.setModifiedAt(LocalDateTime.now().withNano(0));
        Order updatedOrder = orderRepository.save(order);

        // notify frontend
        OrderResponseDTO responseDTO = new OrderResponseDTO(updatedOrder);
        messagingTemplate.convertAndSend("/topic/order-complete", responseDTO);
    }

    private List<Ingredient> updateIngredientInventory(List<DrinkIngredient> drinkIngredients) throws InvalidCredentialsException {
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
        return ingredients;
    }
}
