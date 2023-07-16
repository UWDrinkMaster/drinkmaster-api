package ca.uwaterloo.drinkmasterapi.feature.drink.model;

import ca.uwaterloo.drinkmasterapi.feature.mqtt.model.Machine;
import ca.uwaterloo.drinkmasterapi.feature.user.model.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "drink")
@Getter
@Setter
@ToString
public class Drink {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "image_url")
    private String imageURL;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "price_currency", nullable = false)
    private String priceCurrency;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "is_customized", nullable = false)
    private Boolean isCustomized;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime createdAt;

    @Column(name = "modified_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "machine_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Machine machine;

    @ManyToMany
    @JoinTable(
            name = "drink_ingredient",
            joinColumns = @JoinColumn(name = "drink_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<DrinkIngredient> ingredients;
}
