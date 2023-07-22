package ca.uwaterloo.drinkmasterapi.feature.order.service;

import ca.uwaterloo.drinkmasterapi.feature.order.model.OrderRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.order.model.OrderResponseDTO;

public interface IOrderService {
    OrderResponseDTO createOrder(OrderRequestDTO orderRequest);
}
