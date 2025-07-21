package peaksoft.restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import peaksoft.restaurant.dto.SimpleResponce;
import peaksoft.restaurant.dto.menuItemDTO.request.MenuItemRequest;
import peaksoft.restaurant.dto.menuItemDTO.responce.MenuItemResponce;
import peaksoft.restaurant.service.MenuItemService;

import java.util.List;

@RequestMapping("api/menuItems")
@RestController
@RequiredArgsConstructor
public class MenuItemAPI {
    private final MenuItemService menuItemService;

    @PostMapping()
    public SimpleResponce saveByRestaurantId(@RequestParam Long userId,
                                             @RequestParam Long restaurantId,
                                             @RequestParam Long subCategoryId,
                                             @RequestBody MenuItemRequest menuItemRequest) {
        return menuItemService.createMenuItem(userId, restaurantId, subCategoryId, menuItemRequest);
    }

    @GetMapping("/{id}")
    public MenuItemResponce getById(@PathVariable Long id) {
        return menuItemService.findById(id);
    }


    @GetMapping
    public List<MenuItemResponce> getAll() {
        return menuItemService.findAll();
    }

    @PutMapping
    public SimpleResponce updateById(
            @RequestParam Long menuId,
            @RequestParam Long userId,
            @RequestParam Long restaurantId,
            @RequestBody MenuItemRequest menuItemRequest
    ) {
       return menuItemService.updateMenuItem(menuId,menuItemRequest,userId,restaurantId);
    }

    @DeleteMapping
    public SimpleResponce delete (
            @RequestParam Long menuId,
            @RequestParam Long userId,
            @RequestParam Long restaurantId) {
        return menuItemService.deleteMenuItem(menuId,userId,restaurantId);
    }

    @GetMapping("/search")
    public List<MenuItemResponce> globalSearch(@RequestParam String keyword) {
        return menuItemService.globalSearch(keyword);
    }

    @GetMapping("/sort")
    public List<MenuItemResponce> sortByPrice(@RequestParam Long restaurantId,
                                              @RequestParam(defaultValue = "asc") String ascOrDesc) {
        return menuItemService.sortByPrice(restaurantId, ascOrDesc);
    }

    @GetMapping("/vegetarian")
    public List<MenuItemResponce> getAllVegetarianFood(@RequestParam Long restaurantId) {
        return menuItemService.getAllVegetarianFood(restaurantId);
    }
}
