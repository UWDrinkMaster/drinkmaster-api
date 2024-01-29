package ca.uwaterloo.drinkmasterapi.dao;

import ca.uwaterloo.drinkmasterapi.common.CurrencyEnum;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    private Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "price_currency")
    private CurrencyEnum priceCurrency;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active", nullable = false,  columnDefinition = "BOOLEAN DEFAULT true")
    private Boolean isActive;

    @Column(name = "is_customized", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
    private Boolean isCustomized;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "machine_id")
    private Long machineId;

    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "modified_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifiedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "drink_ingredient",
            joinColumns = @JoinColumn(name = "drink_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "drink", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DrinkIngredient> drinkIngredients;
}
