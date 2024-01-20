package ca.uwaterloo.drinkmasterapi.repository;

import ca.uwaterloo.drinkmasterapi.dao.UserAllergen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAllergenRepository extends JpaRepository<UserAllergen, Long> {
    List<UserAllergen> findUserAllergensByUserId(Long userId);
}
