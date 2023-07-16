package ca.uwaterloo.drinkmasterapi.feature.user.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("username")
    @Column(name = "username")
    private String username;

    @JsonProperty("phone_number")
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @JsonProperty("email")
    @Column(name = "email")
    private String email;

    @JsonProperty("password")
    @Column(name = "password")
    private String password;

    @JsonProperty("is_enabled")
    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @JsonProperty("image_url")
    @Column(name = "image_url")
    private String imageUrl;

    @JsonProperty("date_of_birth")
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @JsonProperty("last_sobriety_test_score")
    @Column(name = "last_sobriety_test_score")
    private Double lastSobrietyTestScore;

    @JsonProperty("last_sobriety_test_at")
    @Column(name = "last_sobriety_test_at")
    private OffsetDateTime lastSobrietyTestAt;

    @JsonProperty("signed_in_at")
    @Column(name = "signed_in_at")
    private OffsetDateTime signedInAt;

    @JsonProperty("created_at")
    @Column(name = "created_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime createdAt;

    @JsonProperty("modified_at")
    @Column(name = "modified_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime modifiedAt;
}
