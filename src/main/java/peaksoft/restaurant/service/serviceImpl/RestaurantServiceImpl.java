package peaksoft.restaurant.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.restaurant.dto.SimpleResponce;
import peaksoft.restaurant.dto.restDto.request.RestaurantRequest;
import peaksoft.restaurant.dto.restDto.response.RestaurantResponce;
import peaksoft.restaurant.entities.Restaurant;
import peaksoft.restaurant.enums.Role;
import peaksoft.restaurant.repo.RestaurantRepo;
import peaksoft.restaurant.service.RestaurantService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepo restaurantRepo;

    @Override
    public SimpleResponce save(RestaurantRequest restaurantRequest) {
        Restaurant restaurant = new Restaurant();
        restaurant.setName(restaurantRequest.name());
        restaurant.setLocation(restaurantRequest.location());
        restaurant.setRestType(restaurantRequest.restType());
        restaurant.setService(restaurantRequest.service());
            restaurantRepo.save(restaurant);

        return SimpleResponce.builder()
                .status(HttpStatus.OK)
                .message("Restaurant saved successfully")
                .build();
    }

    @Override
    public RestaurantResponce findById(Long id) {
        Restaurant restaurant = restaurantRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));
        return RestaurantResponce.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .location(restaurant.getLocation())
                .restType(restaurant.getRestType())
                .numberOfEmployees(restaurant.getNumberOfEmployees())
                .service(restaurant.getService())
                .build();
    }

    @Override
    public List<RestaurantResponce> findAll() {
        List<Restaurant> restaurants = restaurantRepo.findAll();
        List<RestaurantResponce> restaurantResponceList = new ArrayList<>();

        restaurants.forEach(restaurant -> {
            RestaurantResponce restaurantResponce = new RestaurantResponce();
            restaurantResponce.setId(restaurant.getId());
            restaurantResponce.setName(restaurant.getName());
            restaurantResponce.setLocation(restaurant.getLocation());
            restaurantResponce.setRestType(restaurant.getRestType());
            restaurantResponce.setNumberOfEmployees(restaurant.getNumberOfEmployees());
            restaurantResponce.setService(restaurant.getService());
            restaurantResponceList.add(restaurantResponce);
        });
        return restaurantResponceList;
    }

    @Override
    public SimpleResponce update(Long id, RestaurantRequest restaurantRequest) {
        Restaurant restaurant = restaurantRepo.findById(id)
                .orElseThrow(() -> new NullPointerException("Restaurant not found"));
        restaurant.setName(restaurantRequest.name());
        restaurant.setLocation(restaurantRequest.location());
        restaurant.setRestType(restaurantRequest.restType());
        restaurant.setService(restaurantRequest.service());
        if(restaurant.getUsers().size() > 15){
            restaurantRepo.save(restaurant);
        }
        return SimpleResponce.builder()
                .status(HttpStatus.OK)
                .message("Restaurant updated successfully")
                .build();
    }

    @Override
    public SimpleResponce delete(Long id) {
        Restaurant restaurant = restaurantRepo.findById(id)
                .orElseThrow(() -> new NullPointerException("Restaurant not found"));
        restaurantRepo.delete(restaurant);
        return SimpleResponce.builder()
                .status(HttpStatus.OK)
                .message("Restaurant deleted successfully")
                .build();
    }
}
