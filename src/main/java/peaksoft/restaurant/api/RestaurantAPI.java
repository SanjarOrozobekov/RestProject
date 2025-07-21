package peaksoft.restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.restaurant.dto.SimpleResponce;
import peaksoft.restaurant.dto.restDto.request.RestaurantRequest;
import peaksoft.restaurant.dto.restDto.response.RestaurantResponce;
import peaksoft.restaurant.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping("/api/rests")
@RequiredArgsConstructor
public class RestaurantAPI {

    private final RestaurantService restaurantService;

    @PostMapping
    public SimpleResponce saveRestaurant(@RequestBody RestaurantRequest restaurantRequest) {
       return restaurantService.save(restaurantRequest);
    }

    @GetMapping("/{id}")
    public RestaurantResponce getById(@PathVariable Long id) {
        return restaurantService.findById(id);
    }

    @GetMapping
    public List<RestaurantResponce> getAllRestaurants() {
        return restaurantService.findAll();
    }

    @PutMapping("/{id}")
    public SimpleResponce updateRestaurant(@PathVariable Long id, @RequestBody RestaurantRequest restaurantRequest) {
        return restaurantService.update(id,restaurantRequest);
    }

    @DeleteMapping("/{id}")
    public SimpleResponce deleteRestaurant(@PathVariable Long id) {
        return restaurantService.delete(id);
    }
}
