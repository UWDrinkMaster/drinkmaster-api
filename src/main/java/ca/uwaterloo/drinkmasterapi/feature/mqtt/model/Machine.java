package ca.uwaterloo.drinkmasterapi.feature.mqtt.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "machine")
@Getter
@Setter
@ToString
public class Machine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deviceId", nullable = false)
    private String deviceId;

    @Column(name = "privateKey", nullable = false)
    private String privateKey;

    @Column(name = "location", nullable = false)
    private String location;

    @Column(name = "description")
    private String description;

    @Column(name = "createdAt", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime createdAt;

    @Column(name = "modifiedAt", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private OffsetDateTime modifiedAt;
}