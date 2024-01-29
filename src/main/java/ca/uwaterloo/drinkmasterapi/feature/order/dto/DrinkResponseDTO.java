package ca.uwaterloo.drinkmasterapi.feature.order.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class DrinkResponseDTO {
}
