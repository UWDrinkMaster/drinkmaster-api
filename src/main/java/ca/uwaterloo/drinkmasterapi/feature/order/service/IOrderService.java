package ca.uwaterloo.drinkmasterapi.feature.order.service;

import ca.uwaterloo.drinkmasterapi.feature.order.dto.OrderRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.order.dto.OrderResponseDTO;

import java.util.List;

public interface IOrderService {
    OrderResponseDTO createOrder(OrderRequestDTO orderRequest);

    List<OrderResponseDTO> getOrderByUserId(Long userId);

    void updateOrderStatus(Long orderId, boolean isCompleted);
}
