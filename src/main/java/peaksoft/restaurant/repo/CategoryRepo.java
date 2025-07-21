package peaksoft.restaurant.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.restaurant.entities.Category;
@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {
}
