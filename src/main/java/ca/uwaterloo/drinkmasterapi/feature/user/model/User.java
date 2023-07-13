package ca.uwaterloo.drinkmasterapi.feature.user.model;

import ca.uwaterloo.drinkmasterapi.feature.drink.model.Allergy;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "phoneNumber", unique = true)
    private String phoneNumber;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "isEnabled", nullable = false)
    private Boolean isEnabled;

    @Column(name = "imageURL")
    private String imageURL;

    @Column(name = "dateOfBirth")
    private LocalDate dateOfBirth;

    @Column(name = "lastSobrietyTestScore")
    private Double lastSobrietyTestScore;

    @Column(name = "lastSobrietyTestAt")
    private OffsetDateTime lastSobrietyTestAt;

    @Column(name = "signedInAt")
    private OffsetDateTime signedInAt;

    @Column(name = "createdAt", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime createdAt;

    @Column(name = "modifiedAt", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime modifiedAt;

    @ManyToMany
    @JoinTable(
            name = "ingredient_allergy",
            joinColumns = @JoinColumn(name = "userId"),
            inverseJoinColumns = @JoinColumn(name = "allergyId")
    )
    private List<Allergy> allergies;
}
