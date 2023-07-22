package ca.uwaterloo.drinkmasterapi.feature.order.controller;

import ca.uwaterloo.drinkmasterapi.common.ErrorResponseDTO;
import ca.uwaterloo.drinkmasterapi.feature.order.model.OrderRequestDTO;
import ca.uwaterloo.drinkmasterapi.feature.order.model.OrderResponseDTO;
import ca.uwaterloo.drinkmasterapi.feature.order.service.IOrderService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderController {
    private final IOrderService orderService;

    @Autowired
    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses({
            @ApiResponse(code = 201, message = "Signup successful", response = OrderResponseDTO.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponseDTO.class),
            @ApiResponse(code = 404, message = "Not found", response = ErrorResponseDTO.class)
    })
    public ResponseEntity<?> createOrder(@Valid @RequestBody OrderRequestDTO orderRequest) {
        OrderResponseDTO responseDTO = orderService.createOrder(orderRequest);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}
