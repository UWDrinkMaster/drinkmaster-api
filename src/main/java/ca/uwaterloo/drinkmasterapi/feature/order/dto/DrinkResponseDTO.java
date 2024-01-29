package ca.uwaterloo.drinkmasterapi.feature.order.dto;

import ca.uwaterloo.drinkmasterapi.dao.Drink;
import ca.uwaterloo.drinkmasterapi.dao.Ingredient;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DrinkResponseDTO {
    @ApiModelProperty(value = "ID of the drink")
    private Long id;

    @ApiModelProperty(value = "Name of the drink")
    private String name;

    @ApiModelProperty(value = "Image URL of the drink")
    @JsonProperty("image_url")
    private String imageUrl;

    @ApiModelProperty(value = "Price of the drink")
    private double price;

    @ApiModelProperty(value = "Currency of the drink price")
    @JsonProperty("price_currency")
    private String priceCurrency;

    @ApiModelProperty(value = "Category of the drink")
    private String category;

    @ApiModelProperty(value = "Description of the drink")
    private String description;

    @ApiModelProperty(value = "Flag indicating if the drink is available")
    @JsonProperty("is_available")
    private boolean isAvailable;

    @ApiModelProperty(value = "Flag indicating if the drink is active")
    @JsonProperty("is_active")
    private boolean isActive;

    @ApiModelProperty(value = "Flag indicating if the drink is customized")
    @JsonProperty("is_customized")
    private boolean isCustomized;

    @ApiModelProperty(value = "ID of the user associated with the drink")
    @JsonProperty("user_id")
    private Long userId;

    @ApiModelProperty(value = "ID of the machine associated with the drink")
    @JsonProperty("machine_id")
    private Long machineId;

    @ApiModelProperty(value = "Timestamp when the drink was created")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "Timestamp when the drink was last modified")
    @JsonProperty("modified_at")
    private LocalDateTime modifiedAt;

    @ApiModelProperty(value = "List of ingredient IDs associated with the drink")
    @JsonProperty("ingredient_ids")
    private List<Long> ingredientIds;

    @ApiModelProperty(value = "List of allergen IDs associated with the drink")
    @JsonProperty("allergen_ids")
    private List<Long> allergenIds;

    @ApiModelProperty(value = "Flag indicating if the user is allergic to the drink")
    @JsonProperty("is_allergic")
    private boolean isAllergic;

    public DrinkResponseDTO(Drink drink, List<Long> allergenIds, boolean isAvailable, boolean isAllergic) {
        this.id = drink.getId();
        this.name = drink.getName();
        this.imageUrl = drink.getImageUrl();
        this.price = drink.getPrice();
        this.priceCurrency = drink.getPriceCurrency().name();
        this.category = drink.getCategory();
        this.description = drink.getDescription();
        this.isAvailable = isAvailable;
        this.isActive = drink.getIsActive();
        this.isCustomized = drink.getIsCustomized();
        this.userId = drink.getUserId();
        this.machineId = drink.getMachineId();
        this.createdAt = drink.getCreatedAt();
        this.modifiedAt = drink.getModifiedAt();

        this.ingredientIds = drink.getIngredients().stream()
                .map(Ingredient::getId)
                .collect(Collectors.toList());

        this.allergenIds = allergenIds;
        this.isAllergic = isAllergic;
    }
}