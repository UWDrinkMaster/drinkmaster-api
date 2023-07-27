package ca.uwaterloo.drinkmasterapi.feature.order;

import ca.uwaterloo.drinkmasterapi.common.ResourceNotFoundException;
import ca.uwaterloo.drinkmasterapi.dao.Drink;
import ca.uwaterloo.drinkmasterapi.dao.Order;
import ca.uwaterloo.drinkmasterapi.repository.DrinkRepository;
import ca.uwaterloo.drinkmasterapi.dao.Machine;
import ca.uwaterloo.drinkmasterapi.repository.MachineRepository;
import ca.uwaterloo.drinkmasterapi.feature.order.model.*;
import ca.uwaterloo.drinkmasterapi.repository.OrderRepository;
import ca.uwaterloo.drinkmasterapi.feature.order.service.OrderServiceImpl;
import ca.uwaterloo.drinkmasterapi.dao.User;
import ca.uwaterloo.drinkmasterapi.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
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

    @Test
    public void testGetOrderByUserId_WithValidUserId_Success() {
        // Arrange
        Long userId = 1L;

        List<Order> userOrders = new ArrayList<>();
        Order order1 = new Order();
        order1.setId(1L);
        Order order2 = new Order();
        order2.setId(2L);
        userOrders.add(order1);
        userOrders.add(order2);

        when(userRepository.findById(userId)).thenReturn(Optional.of(new User()));
        when(orderRepository.findByUserId(userId)).thenReturn(userOrders);

        // Act
        List<OrderResponseDTO> responseDTOs = orderService.getOrderByUserId(userId);

        // Assert
        assertNotNull(responseDTOs);
        assertEquals(2, responseDTOs.size());
        assertEquals(1L, responseDTOs.get(0).getId());
        assertEquals(2L, responseDTOs.get(1).getId());
    }

    @Test
    public void testGetOrderByUserId_InvalidUserId_ThrowsResourceNotFoundException() {
        // Arrange
        Long invalidUserId = 999L;

        // Mock the userRepository to return an empty Optional, indicating that the user doesn't exist
        when(userRepository.findById(invalidUserId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> orderService.getOrderByUserId(invalidUserId));
    }
}
