package ca.uwaterloo.drinkmasterapi.feature.mqtt.repository;

import ca.uwaterloo.drinkmasterapi.dao.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineRepository extends JpaRepository<Machine, Long> {
}
