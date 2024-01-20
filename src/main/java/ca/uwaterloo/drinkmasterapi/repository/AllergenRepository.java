package ca.uwaterloo.drinkmasterapi.repository;

import ca.uwaterloo.drinkmasterapi.dao.Allergen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllergenRepository extends JpaRepository<Allergen, Long> {
    List<Allergen> findAll();
}
