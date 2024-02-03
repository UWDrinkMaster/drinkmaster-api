package ca.uwaterloo.drinkmasterapi.repository;

import ca.uwaterloo.drinkmasterapi.dao.DrinkIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkIngredientRepository extends JpaRepository<DrinkIngredient, Long> {
}
