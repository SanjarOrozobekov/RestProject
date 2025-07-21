package peaksoft.restaurant.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.restaurant.entities.StopList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface StoplistRepo extends JpaRepository<StopList,Long> {
    @Query("""
        select s from StopList s
        where s.date = :date and s.menuItem.id = :menuItemId
    """)
    Optional<StopList> existsByDateAndMenuItemId(@Param("date") LocalDateTime date,
                                                 @Param("menuItemId") Long menuItemId);
}
