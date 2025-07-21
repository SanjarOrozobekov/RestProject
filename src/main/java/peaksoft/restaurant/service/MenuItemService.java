package peaksoft.restaurant.service;

import org.springframework.stereotype.Service;
import peaksoft.restaurant.dto.SimpleResponce;
import peaksoft.restaurant.dto.menuItemDTO.request.MenuItemRequest;
import peaksoft.restaurant.dto.menuItemDTO.responce.MenuItemResponce;

import java.util.List;

@Service
public interface MenuItemService {
    SimpleResponce createMenuItem(Long userId, Long restaurantId, Long subCategoryId, MenuItemRequest menuItemRequest);
    MenuItemResponce findById(Long id);
    List<MenuItemResponce> findAll();
    SimpleResponce updateMenuItem(Long menuId,MenuItemRequest menuItemRequest, Long userId, Long restaurantId);
    SimpleResponce deleteMenuItem(Long menuId,Long userId, Long restaurantId);
    List<MenuItemResponce> globalSearch(String keyword);
    List<MenuItemResponce> sortByPrice(Long restaurantId, String ascOrDesc);
    List<MenuItemResponce> getAllVegetarianFood(Long restaurantId);
}
