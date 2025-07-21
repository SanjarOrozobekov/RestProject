package peaksoft.restaurant.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import peaksoft.restaurant.dto.SimpleResponce;
import peaksoft.restaurant.dto.menuItemDTO.request.MenuItemRequest;
import peaksoft.restaurant.dto.menuItemDTO.responce.MenuItemResponce;
import peaksoft.restaurant.entities.MenuItem;
import peaksoft.restaurant.entities.Restaurant;
import peaksoft.restaurant.entities.Subcategory;
import peaksoft.restaurant.entities.User;
import peaksoft.restaurant.enums.Role;
import peaksoft.restaurant.repo.MenuItemRepo;
import peaksoft.restaurant.repo.RestaurantRepo;
import peaksoft.restaurant.repo.SubcategoryRepo;
import peaksoft.restaurant.repo.UserRepo;
import peaksoft.restaurant.service.MenuItemService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuItemServiceImpl implements MenuItemService {
    private  final MenuItemRepo menuItemRepo;
    private final RestaurantRepo restaurantRepo;
    private final UserRepo userRepo;
    private final SubcategoryRepo subcategoryRepo;

    @Override
    public SimpleResponce createMenuItem(Long userId, Long restaurantId, Long subCategoryId, MenuItemRequest menuItemRequest) {
        User user = userRepo.findById(userId).orElseThrow(
                () -> new NullPointerException(String.format("User with id %s not found", userId))
        );
        if (user.getRole().equals(Role.WAITER)) {
            return SimpleResponce.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("You are not allowed to save this menu item")
                    .build();
        }

        Restaurant restaurant = restaurantRepo.findById(restaurantId).orElseThrow(
                () -> new NullPointerException(String.format("Restaurant with id %s not found", restaurantId))
        );

        if (menuItemRequest.price() < 0){
            return SimpleResponce.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Price must be bigger than zero")
                    .build();
        }

        MenuItem menuItem = MenuItem.builder()
                .name(menuItemRequest.name())
                .description(menuItemRequest.description())
                .price(menuItemRequest.price())
                .description(menuItemRequest.description())
                .image(menuItemRequest.image())
                .isVegetarian(menuItemRequest.isVegetarian())
                .build();

        Subcategory subCategory =subcategoryRepo.findById(subCategoryId).orElseThrow(
                () -> new NullPointerException(String.format("SubCategory with id %s not found", subCategoryId))
        );

        menuItem.setRestaurant(restaurant);
        restaurant.getMenuItems().add(menuItem);
        menuItem.setSubcategory(subCategory);
        subCategory.getMenuItems().add(menuItem);
        menuItemRepo.save(menuItem);

        return SimpleResponce.builder()
                .status(HttpStatus.CREATED)
                .message("Menu item saved")
                .build();
    }

    @Override
    public MenuItemResponce findById(Long id) {
        MenuItem menuItem = menuItemRepo.findById(id)
                .orElseThrow(()->new RuntimeException("menu item not found"));
        return MenuItemResponce.builder()
                .id(menuItem.getId())
                .name(menuItem.getName())
                .description(menuItem.getDescription())
                .image(menuItem.getImage())
                .price(menuItem.getPrice())
                .isVegetarian(menuItem.isVegetarian())
                .build();
    }

    @Override
    public List<MenuItemResponce> findAll() {
        List<MenuItem> menuItems = menuItemRepo.findAll();
        List<MenuItemResponce> menuItemResponceList = new ArrayList<>();
        menuItems.forEach(menuItem -> {
            MenuItemResponce menuItemResponce = new MenuItemResponce();
            menuItemResponce.setId(menuItem.getId());
            menuItemResponce.setName(menuItem.getName());
            menuItemResponce.setDescription(menuItem.getDescription());
            menuItemResponce.setImage(menuItem.getImage());
            menuItemResponce.setPrice(menuItem.getPrice());
            menuItemResponce.setVegetarian(menuItem.isVegetarian());
            menuItemResponceList.add(menuItemResponce);
        });
        return  menuItemResponceList;
    }

    @Override
    public SimpleResponce updateMenuItem(Long menuId, MenuItemRequest menuItemRequest, Long userId, Long restaurantId) {
        MenuItem menuItem = menuItemRepo.findById(menuId)
                .orElseThrow(()->new RuntimeException("menu item not found"));
        User user=userRepo.findById(userId)
                .orElseThrow(()->new RuntimeException("user not found"));
        if (!(user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.CHEF))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Only ADMIN or CHEF can create a menu item");
        }
        menuItem.setName(menuItemRequest.name());
        menuItem.setDescription(menuItemRequest.description());
        menuItem.setImage(menuItemRequest.image());
        menuItem.setPrice(menuItemRequest.price());
        menuItem.setVegetarian(menuItemRequest.isVegetarian());
        menuItemRepo.save(menuItem);
        return SimpleResponce.builder()
                .status(HttpStatus.OK)
                .message("Menu item updated successfully")
                .build();
    }

    @Override
    public SimpleResponce deleteMenuItem(Long menuId, Long userId, Long restaurantId) {
        MenuItem menuItem = menuItemRepo.findById(menuId)
                .orElseThrow(() -> new RuntimeException("menu item not found"));

        if (!menuItem.getRestaurant().getId().equals(restaurantId)) {
            throw new RuntimeException("restaurant not found");
        }

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("user not found"));

        if (!(user.getRole().equals(Role.ADMIN) || user.getRole().equals(Role.CHEF))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Only ADMIN or CHEF can delete a menu item");
        }

        menuItemRepo.delete(menuItem);
        return SimpleResponce.builder()
                .status(HttpStatus.OK)
                .message("menu item deleted successfully")
                .build();
    }
    @Override
    public List<MenuItemResponce> globalSearch(String keyword) {
        return menuItemRepo.globalSearch(keyword);
    }

    @Override
    public List<MenuItemResponce> sortByPrice(Long restaurantId, String ascOrDesc) {
        if (ascOrDesc.equals("desc")) {
            return menuItemRepo.sortByPriceDesc(restaurantId);
        } else if("asc".equals(ascOrDesc)) {
            return menuItemRepo.sortByPriceAsc(restaurantId);
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<MenuItemResponce> getAllVegetarianFood(Long restaurantId) {
        return menuItemRepo.getAllVegetarianFood(restaurantId);
    }
}
