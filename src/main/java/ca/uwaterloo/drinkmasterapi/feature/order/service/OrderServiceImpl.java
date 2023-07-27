package ca.uwaterloo.drinkmasterapi.feature.order.service;

import ca.uwaterloo.drinkmasterapi.handler.exception.ResourceNotFoundException;
import ca.uwaterloo.drinkmasterapi.dao.Drink;
import ca.uwaterloo.drinkmasterapi.repository.DrinkRepository;
import ca.uwaterloo.drinkmasterapi.repository.MachineRepository;
import ca.uwaterloo.drinkmasterapi.dao.Order;
import ca.uwaterloo.drinkmasterapi.feature.order.dto.OrderRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.order.dto.OrderResponseDTO;
import ca.uwaterloo.drinkmasterapi.common.OrderStatusEnum;
import ca.uwaterloo.drinkmasterapi.repository.OrderRepository;
import ca.uwaterloo.drinkmasterapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements IOrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final DrinkRepository drinkRepository;
    private final MachineRepository machineRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository,
                            DrinkRepository drinkRepository,
                            MachineRepository machineRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.drinkRepository = drinkRepository;
        this.machineRepository = machineRepository;
    }

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequest) {
        // Check if userId, drinkId, and machineId exist
        userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + orderRequest.getUserId() + " not found."));
        Drink drink = drinkRepository.findById(orderRequest.getDrinkId())
                .orElseThrow(() -> new ResourceNotFoundException ("Drink with ID " + orderRequest.getDrinkId() + " not found."));
        machineRepository.findById(orderRequest.getMachineId())
                .orElseThrow(() -> new ResourceNotFoundException ("Machine with ID " + orderRequest.getMachineId() + " not found."));

        // Calculate the total price based on the drink price and quantity
        double totalPrice = drink.getPrice() * orderRequest.getQuantity();

        // Create the order entity and set the attributes
        Order order = new Order();
        LocalDateTime currentTime = LocalDateTime.now().withNano(0);

        order.setMachineId(orderRequest.getMachineId());
        order.setUserId(orderRequest.getUserId());
        order.setDrinkId(orderRequest.getDrinkId());
        order.setQuantity(orderRequest.getQuantity());
        order.setPrice(totalPrice);
        order.setPriceCurrency(drink.getPriceCurrency());
        order.setStatus(OrderStatusEnum.CREATED);
        order.setCreatedAt(currentTime);
        order.setModifiedAt(currentTime);

        Order savedOrder = orderRepository.save(order);

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
}
