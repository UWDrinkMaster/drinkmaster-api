package ca.uwaterloo.drinkmasterapi.feature.user.dto;

import ca.uwaterloo.drinkmasterapi.dao.Allergen;
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
public class AllergenResponseDTO {
    @ApiModelProperty(value = "ID of the allergen", example = "1")
    private Long id;

    @ApiModelProperty(value = "Name of the allergen", example = "NUTS")
    private String name;

    @ApiModelProperty(value = "Description of the allergen", example = "May contain traces of nuts.")
    private String description;

    @ApiModelProperty(value = "Timestamp when the allergen was created")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "Timestamp when the allergen was last modified")
    @JsonProperty("modified_at")
    private LocalDateTime modifiedAt;

    public AllergenResponseDTO(Allergen allergen) {
        this.id = allergen.getId();
        this.name = allergen.getName();
        this.description = allergen.getDescription();
        this.createdAt = allergen.getCreatedAt();
        this.modifiedAt = allergen.getModifiedAt();
    }
}
