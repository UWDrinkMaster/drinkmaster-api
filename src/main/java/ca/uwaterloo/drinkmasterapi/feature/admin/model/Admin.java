package ca.uwaterloo.drinkmasterapi.feature.admin.model;

import ca.uwaterloo.drinkmasterapi.feature.mqtt.model.Machine;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "admin")
@Getter
@Setter
@ToString
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "isEnabled", nullable = false)
    private Boolean isEnabled;

    @Column(name = "imageURL")
    private String imageURL;

    @Column(name = "signedInAt")
    private OffsetDateTime signedInAt;

    @Column(name = "createdAt", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime createdAt;

    @Column(name = "modifiedAt", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime modifiedAt;

    @ManyToMany
    @JoinTable(
            name = "admin_machine",
            joinColumns = @JoinColumn(name = "admin_id"),
            inverseJoinColumns = @JoinColumn(name = "machine_id")
    )
    private List<Machine> machines;
}
