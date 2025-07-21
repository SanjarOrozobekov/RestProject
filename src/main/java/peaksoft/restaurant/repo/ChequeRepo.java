package peaksoft.restaurant.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.restaurant.dto.chequeDTO.responce.ChegueResponce;
import peaksoft.restaurant.entities.Cheque;

import java.util.List;

@Repository
public interface ChequeRepo extends JpaRepository<Cheque,Long> {
    @Query("""
            select new peaksoft.restaurant.dto.chequeDTO.responce.ChegueResponce (
                        c.id,c.priceAverage,c.date,c.service,c.grandTotal
                        )
            from Cheque c
            """)
    List<ChegueResponce> getAll();

    @Query("""
            select new peaksoft.restaurant.dto.chequeDTO.responce.ChegueResponce (
                        c.id,c.priceAverage,c.date,c.service,c.grandTotal
                        )
            from Cheque c
            where c.user.id = :userId
            """)
    List<ChegueResponce> getAllChequesByUserId(Long userId);

    @Query(value = """
            select ch.* from cheques ch
            join users u on ch.user_id = u.id
            join restaurants r on u.restaurant_id = r.id
            where r.id = :restaurantId
            """, nativeQuery = true)
    List<Cheque> getAllChequesByRestaurantId(Long restaurantId);
}
