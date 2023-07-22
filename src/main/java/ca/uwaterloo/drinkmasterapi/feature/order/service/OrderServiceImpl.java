package ca.uwaterloo.drinkmasterapi.feature.order.service;

import ca.uwaterloo.drinkmasterapi.common.ResourceNotFoundException;
import ca.uwaterloo.drinkmasterapi.feature.drink.model.Drink;
import ca.uwaterloo.drinkmasterapi.feature.drink.repository.DrinkRepository;
import ca.uwaterloo.drinkmasterapi.feature.mqtt.model.Machine;
import ca.uwaterloo.drinkmasterapi.feature.mqtt.repository.MachineRepository;
import ca.uwaterloo.drinkmasterapi.feature.order.model.Order;
import ca.uwaterloo.drinkmasterapi.feature.order.model.OrderRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.order.model.OrderResponseDTO;
import ca.uwaterloo.drinkmasterapi.feature.order.model.OrderStatusEnum;
import ca.uwaterloo.drinkmasterapi.feature.order.repository.OrderRepository;
import ca.uwaterloo.drinkmasterapi.feature.user.model.User;
import ca.uwaterloo.drinkmasterapi.feature.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        User user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + orderRequest.getUserId() + " not found."));
        Drink drink = drinkRepository.findById(orderRequest.getDrinkId())
                .orElseThrow(() -> new ResourceNotFoundException ("Drink with ID " + orderRequest.getDrinkId() + " not found."));
        Machine machine = machineRepository.findById(orderRequest.getMachineId())
                .orElseThrow(() -> new ResourceNotFoundException ("Machine with ID " + orderRequest.getMachineId() + " not found."));

        // Calculate the total price based on the drink price and quantity
        double totalPrice = drink.getPrice() * orderRequest.getQuantity();

        // Create the order entity and set the attributes
        Order order = new Order();
        order.setMachineId(orderRequest.getMachineId());
        order.setUserId(orderRequest.getUserId());
        order.setDrinkId(orderRequest.getDrinkId());
        order.setQuantity(orderRequest.getQuantity());
        order.setPrice(totalPrice);
        order.setPriceCurrency(drink.getPriceCurrency());
        order.setStatus(OrderStatusEnum.CREATED);

        Order savedOrder = orderRepository.save(order);

        return new OrderResponseDTO(savedOrder);
    }
}
