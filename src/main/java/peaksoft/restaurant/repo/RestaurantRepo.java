package peaksoft.restaurant.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.restaurant.entities.Restaurant;
@Repository
public interface RestaurantRepo extends JpaRepository<Restaurant,Long> {
}
