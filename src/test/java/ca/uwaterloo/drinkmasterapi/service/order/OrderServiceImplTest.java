package ca.uwaterloo.drinkmasterapi.service.order;

import ca.uwaterloo.drinkmasterapi.common.ResourceNotFoundException;
import ca.uwaterloo.drinkmasterapi.feature.drink.model.Drink;
import ca.uwaterloo.drinkmasterapi.feature.drink.repository.DrinkRepository;
import ca.uwaterloo.drinkmasterapi.feature.mqtt.model.Machine;
import ca.uwaterloo.drinkmasterapi.feature.mqtt.repository.MachineRepository;
import ca.uwaterloo.drinkmasterapi.feature.order.model.*;
import ca.uwaterloo.drinkmasterapi.feature.order.repository.OrderRepository;
import ca.uwaterloo.drinkmasterapi.feature.order.service.OrderServiceImpl;
import ca.uwaterloo.drinkmasterapi.feature.user.model.User;
import ca.uwaterloo.drinkmasterapi.feature.user.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class OrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DrinkRepository drinkRepository;

    @Mock
    private MachineRepository machineRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void testCreateOrder_WithValidData_Success() {
        // Arrange
        OrderRequestDTO orderRequest = new OrderRequestDTO();
        orderRequest.setUserId(1L);
        orderRequest.setDrinkId(2L);
        orderRequest.setMachineId(3L);

        Order savedOrder = new Order();
        savedOrder.setId(4L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(drinkRepository.findById(anyLong())).thenReturn(Optional.of(new Drink()));
        when(machineRepository.findById(anyLong())).thenReturn(Optional.of(new Machine()));
        when(orderRepository.save(any(Order.class))).thenReturn(savedOrder);

        // Act
        OrderResponseDTO orderResponseDTO = orderService.createOrder(orderRequest);

        // Assert
        assertNotNull(orderResponseDTO);
        assertEquals(4L, orderResponseDTO.getId());
    }

    @Test
    public void testCreateOrder_InvalidUserId_ThrowsResourceNotFoundException() {
        // Arrange
        Long invalidUserId = 999L;

        OrderRequestDTO orderRequest = new OrderRequestDTO();
        orderRequest.setUserId(invalidUserId);
        orderRequest.setDrinkId(2L);
        orderRequest.setMachineId(3L);

        when(userRepository.findById(invalidUserId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(orderRequest));
    }

    @Test
    public void testCreateOrder_InvalidDrinkId_ThrowsResourceNotFoundException() {
        // Arrange
        Long invalidDrinkId = 999L;

        OrderRequestDTO orderRequest = new OrderRequestDTO();
        orderRequest.setUserId(1L);
        orderRequest.setDrinkId(invalidDrinkId);
        orderRequest.setMachineId(3L);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(drinkRepository.findById(invalidDrinkId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(orderRequest));
    }

    @Test
    public void testCreateOrder_InvalidMachineId_ThrowsResourceNotFoundException() {
        // Arrange
        Long invalidMachineId = 999L;

        OrderRequestDTO orderRequest = new OrderRequestDTO();
        orderRequest.setUserId(1L);
        orderRequest.setDrinkId(2L);
        orderRequest.setMachineId(invalidMachineId);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new User()));
        when(drinkRepository.findById(anyLong())).thenReturn(Optional.of(new Drink()));
        when(machineRepository.findById(invalidMachineId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> orderService.createOrder(orderRequest));
    }
}
