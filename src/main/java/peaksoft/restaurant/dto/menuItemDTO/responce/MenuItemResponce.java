package peaksoft.restaurant.dto.menuItemDTO.responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class MenuItemResponce {
    Long id;
    String name;
    String image;
    int price;
    String description;
    boolean isVegetarian;

    public MenuItemResponce(Long id, String name, String image, int price, String description, boolean isVegetarian) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.description = description;
        this.isVegetarian = isVegetarian;
    }
}
