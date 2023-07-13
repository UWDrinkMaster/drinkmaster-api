package ca.uwaterloo.drinkmasterapi.feature.drink.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "drink_ingredient")
@Getter
@Setter
@ToString
public class DrinkIngredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "drinkId")
    private Drink drink;

    @ManyToOne
    @JoinColumn(name = "ingredientId")
    private Ingredient ingredient;

    @Column(name = "quantity", nullable = false, columnDefinition = "DOUBLE DEFAULT 0.0")
    private Double quantity;

    @Column(name = "createdAt", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime createdAt;

    @Column(name = "modifiedAt", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime modifiedAt;
}