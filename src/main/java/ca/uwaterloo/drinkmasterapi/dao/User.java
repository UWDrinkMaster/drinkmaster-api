package ca.uwaterloo.drinkmasterapi.dao;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "last_sobriety_test_score")
    private Double lastSobrietyTestScore;

    @Column(name = "last_sobriety_test_at")
    private LocalDateTime lastSobrietyTestAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_allergen",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "allergen_id")
    )
    private List<Allergen> allergens;

    @Column(name = "signed_in_at")
    private LocalDateTime signedInAt;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "modified_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifiedAt;
}
