package ca.uwaterloo.drinkmasterapi.feature.admin.model;

import ca.uwaterloo.drinkmasterapi.feature.mqtt.model.Machine;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin_machine")
@Getter
@Setter
@ToString
public class AdminMachine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "adminId")
    private Admin admin;

    @ManyToOne
    @JoinColumn(name = "machineId")
    private Machine machine;

    @Column(name = "createdAt", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "modifiedAt", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime modifiedAt;
}

