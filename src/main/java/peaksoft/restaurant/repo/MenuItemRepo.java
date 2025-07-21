package peaksoft.restaurant.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import peaksoft.restaurant.dto.menuItemDTO.responce.MenuItemResponce;
import peaksoft.restaurant.entities.MenuItem;

import java.util.List;

@Repository
public interface MenuItemRepo extends JpaRepository<MenuItem,Long> {
    @Query("""
                SELECT new peaksoft.restaurant.dto.menuItemDTO.responce.MenuItemResponce(
                m.id, m.name, m.image, m.price, m.description, m.isVegetarian)
                FROM MenuItem m
                WHERE LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """)
    List<MenuItemResponce> globalSearch(@Param("keyword") String keyword);

    @Query("""
            select new peaksoft.restaurant.dto.menuItemDTO.responce.MenuItemResponce(
            m.id, m.name, m.image, m.price, m.description, m.isVegetarian)
            from MenuItem m
            where m.restaurant.id = :restaurantId
            order by m.price desc
            """)
    List<MenuItemResponce> sortByPriceDesc(@Param("restaurantId") Long restaurantId);

    @Query("""
            select new peaksoft.restaurant.dto.menuItemDTO.responce.MenuItemResponce(
            m.id, m.name, m.image, m.price, m.description, m.isVegetarian)
            from MenuItem m
            where m.restaurant.id = :restaurantId
            order by m.price
            """)
    List<MenuItemResponce> sortByPriceAsc(@Param("restaurantId") Long restaurantId);

    @Query("""
            select new peaksoft.restaurant.dto.menuItemDTO.responce.MenuItemResponce(
            m.id, m.name, m.image, m.price, m.description, m.isVegetarian)
            from MenuItem m
            where m.restaurant.id = :restaurantId
            and m.isVegetarian = true
            """)
    List<MenuItemResponce> getAllVegetarianFood(Long restaurantId);
}
