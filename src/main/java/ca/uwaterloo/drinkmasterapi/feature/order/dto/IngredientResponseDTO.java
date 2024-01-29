package ca.uwaterloo.drinkmasterapi.feature.order.dto;

import ca.uwaterloo.drinkmasterapi.dao.Allergen;
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
public class IngredientResponseDTO {
    @ApiModelProperty(value = "ID of the ingredient", example = "1")
    private Long id;

    @ApiModelProperty(value = "Name of the ingredient", example = "Vodka")
    private String name;

    @ApiModelProperty(value = "ID of the machine associated with the ingredient", example = "1")
    @JsonProperty("machine_id")
    private Long machineId;

    @ApiModelProperty(value = "Position of the ingredient", example = "21")
    private int position;

    @ApiModelProperty(value = "Description of the ingredient", example = "A neutral and affordable distilled spirit.")
    private String description;

    @ApiModelProperty(value = "Inventory of the ingredient", example = "1000.0")
    private double inventory;

    @ApiModelProperty(value = "Flag indicating if the ingredient is active", example = "1")
    @JsonProperty("is_active")
    private boolean isActive;

    @ApiModelProperty(value = "List of allergen IDs associated with the ingredient")
    @JsonProperty("allergen_ids")
    private List<Long> allergenIds;

    @ApiModelProperty(value = "Timestamp when the ingredient was created")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "Timestamp when the ingredient was last modified")
    @JsonProperty("modified_at")
    private LocalDateTime modifiedAt;

    public IngredientResponseDTO(Ingredient ingredient) {
        this.id = ingredient.getId();
        this.name = ingredient.getName();
        this.machineId = ingredient.getMachine().getId();
        this.position = ingredient.getPosition();
        this.description = ingredient.getDescription();
        this.inventory = ingredient.getInventory();
        this.isActive = ingredient.getIsActive();
        this.allergenIds = ingredient.getAllergens().stream()
                .map(Allergen::getId)
                .collect(Collectors.toList());
        this.createdAt = ingredient.getCreatedAt();
        this.modifiedAt = ingredient.getModifiedAt();
    }
}
