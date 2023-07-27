package ca.uwaterloo.drinkmasterapi.feature.drink.repository;

import ca.uwaterloo.drinkmasterapi.dao.Drink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DrinkRepository extends JpaRepository<Drink, Long> {
}
