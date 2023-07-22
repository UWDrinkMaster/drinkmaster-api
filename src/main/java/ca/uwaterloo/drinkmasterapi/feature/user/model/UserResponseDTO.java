package ca.uwaterloo.drinkmasterapi.feature.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserResponseDTO {
    @JsonProperty("id")
    @ApiModelProperty(value = "User ID", example = "1")
    private Long id;

    @JsonProperty("username")
    @ApiModelProperty(value = "Username", example = "john_doe")
    private String username;

    @JsonProperty("phone_number")
    @ApiModelProperty(value = "Phone number", example = "+12345678900")
    private String phoneNumber;

    @JsonProperty("email")
    @ApiModelProperty(value = "Email address", example = "user@example.com")
    private String email;

    @JsonProperty("is_enabled")
    @ApiModelProperty(value = "Flag indicating whether the user is enabled", example = "true")
    private Boolean isEnabled;

    @JsonProperty("image_url")
    @ApiModelProperty(value = "Image URL", example = "https://example.com/image.jpg")
    private String imageUrl;

    @JsonProperty("date_of_birth")
    private LocalDate dateOfBirth;

    @JsonProperty("last_sobriety_test_score")
    @ApiModelProperty(value = "Last sobriety test score", example = "90.5")
    private Double lastSobrietyTestScore;

    @JsonProperty("last_sobriety_test_at")
    private LocalDateTime lastSobrietyTestAt;

    @JsonProperty("signed_in_at")
    private LocalDateTime signedInAt;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("modified_at")
    private LocalDateTime modifiedAt;

    // Constructor to convert from User to LoginResponseDTO
    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.isEnabled = user.getIsEnabled();
        this.imageUrl = user.getImageUrl();
        this.dateOfBirth = user.getDateOfBirth();
        this.lastSobrietyTestScore = user.getLastSobrietyTestScore();
        this.lastSobrietyTestAt = user.getLastSobrietyTestAt();
        this.signedInAt = user.getSignedInAt();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
    }
}
