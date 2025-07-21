package peaksoft.restaurant.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.restaurant.entities.Subcategory;

import java.util.List;

@Repository
public interface SubcategoryRepo extends JpaRepository<Subcategory,Long> {
    @Query("SELECT sc FROM Subcategory sc WHERE sc.category.id = :categoryId ORDER BY sc.name ASC")
    List<Subcategory> findByCategoryIdOrderByNameAsc(@Param("categoryId") Long categoryId);

    @Query("SELECT sc FROM Subcategory sc JOIN sc.category c ORDER BY c.name ASC, sc.name ASC")
    List<Subcategory> findAllByOrderByCategoryNameAscNameAsc();

}
