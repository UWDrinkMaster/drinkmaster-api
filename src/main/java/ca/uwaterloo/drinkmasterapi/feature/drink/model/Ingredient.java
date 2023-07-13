package ca.uwaterloo.drinkmasterapi.feature.drink.model;

import ca.uwaterloo.drinkmasterapi.feature.mqtt.model.Machine;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "ingredient")
@Getter
@Setter
@ToString
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "position", nullable = false)
    private int position;

    @Column(name = "description")
    private String description;

    @Column(name = "inventory", columnDefinition = "DOUBLE DEFAULT 0.0")
    private Double inventory;

    @Column(name = "isActive", nullable = false)
    private Boolean isActive;

    @Column(name = "createdAt", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime createdAt;

    @Column(name = "modifiedAt", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime modifiedAt;

    @ManyToMany
    @JoinTable(
            name = "ingredient_allergy",
            joinColumns = @JoinColumn(name = "ingredientId"),
            inverseJoinColumns = @JoinColumn(name = "allergyId")
    )
    private List<Allergy> allergies;

    @ManyToOne
    @JoinColumn(name = "machineId", referencedColumnName = "id", insertable = false, updatable = false)
    private Machine machine;
}