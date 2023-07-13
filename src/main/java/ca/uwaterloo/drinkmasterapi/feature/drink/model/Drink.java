package ca.uwaterloo.drinkmasterapi.feature.drink.model;

import ca.uwaterloo.drinkmasterapi.feature.mqtt.model.Machine;
import ca.uwaterloo.drinkmasterapi.feature.user.model.User;
import jdk.internal.net.http.common.Pair;
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

    @Column(name = "imageURL")
    private String imageURL;

    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "priceCurrency", nullable = false)
    private String priceCurrency;

    @Column(name = "category")
    private String category;

    @Column(name = "description")
    private String description;

    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    @Column(name = "isCustomized", nullable = false)
    private Boolean isCustomized;

    @Column(name = "createdAt", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime createdAt;

    @Column(name = "modifiedAt", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime modifiedAt;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "id", insertable = false, updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "machineId", referencedColumnName = "id", insertable = false, updatable = false)
    private Machine machine;

    @ManyToMany
    @JoinTable(
            name = "drink_ingredient",
            joinColumns = @JoinColumn(name = "drinkId"),
            inverseJoinColumns = @JoinColumn(name = "ingredientId")
    )
    private List<DrinkIngredient> ingredients;
}