package ca.uwaterloo.drinkmasterapi.feature.order.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "`order`")
@Getter
@Setter
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "machine_id")
    private Long machineId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "drink_id")
    private Long drinkId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "price_currency")
    private CurrencyEnum priceCurrency;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatusEnum status;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "modified_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifiedAt;
}
