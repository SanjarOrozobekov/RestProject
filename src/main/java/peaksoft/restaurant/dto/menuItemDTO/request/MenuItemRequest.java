package peaksoft.restaurant.dto.menuItemDTO.request;

public record MenuItemRequest (
        String name,
        String image,
        int price,
        String description,
        boolean isVegetarian
){
}
