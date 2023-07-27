package ca.uwaterloo.drinkmasterapi.feature.order.model;

import ca.uwaterloo.drinkmasterapi.common.CurrencyEnum;
import ca.uwaterloo.drinkmasterapi.common.OrderStatusEnum;
import ca.uwaterloo.drinkmasterapi.dao.Order;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class OrderResponseDTO {
    @ApiModelProperty(value = "ID of the order", example = "1")
    @JsonProperty("id")
    private Long id;

    @ApiModelProperty(value = "ID of the machine", example = "101")
    @JsonProperty("machine_id")
    private Long machineId;

    @ApiModelProperty(value = "ID of the user", example = "2001")
    @JsonProperty("user_id")
    private Long userId;

    @ApiModelProperty(value = "ID of the drink", example = "3001")
    @JsonProperty("drink_id")
    private Long drinkId;

    @ApiModelProperty(value = "Quantity of the drink", example = "2")
    @JsonProperty("quantity")
    private int quantity;

    @ApiModelProperty(value = "Price of the order", example = "12.99")
    @JsonProperty("price")
    private double price;

    @ApiModelProperty(value = "Currency of the price", example = "CAD")
    @JsonProperty("price_currency")
    private CurrencyEnum priceCurrency;

    @ApiModelProperty(value = "Status of the order", example = "COMPLETED")
    @JsonProperty("status")
    private OrderStatusEnum status;

    @ApiModelProperty(value = "Timestamp when the order was created")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "Timestamp when the order was last modified")
    @JsonProperty("modified_at")
    private LocalDateTime modifiedAt;

    public OrderResponseDTO(Order order) {
        this.id = order.getId();
        this.machineId = order.getMachineId();
        this.userId = order.getUserId();
        this.drinkId = order.getDrinkId();
        this.quantity = order.getQuantity();
        this.price = order.getPrice();
        this.priceCurrency = order.getPriceCurrency();
        this.status = order.getStatus();
        this.createdAt = order.getCreatedAt();
        this.modifiedAt = order.getModifiedAt();
    }
}
