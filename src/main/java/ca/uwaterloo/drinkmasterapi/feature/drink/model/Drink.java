package ca.uwaterloo.drinkmasterapi.feature.drink.model;

import ca.uwaterloo.drinkmasterapi.feature.order.model.CurrencyEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "drink")
@Getter
@Setter
@Data
public class Drink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "price")
    private double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "price_currency")
    private CurrencyEnum priceCurrency;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active", columnDefinition = "BOOLEAN DEFAULT true")
    private boolean isActive = true;

    @Column(name = "is_customized", columnDefinition = "BOOLEAN DEFAULT false")
    private boolean isCustomized = false;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "machine_id")
    private Long machineId;

    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "modified_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifiedAt;
}
