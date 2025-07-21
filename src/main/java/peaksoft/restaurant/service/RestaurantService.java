package peaksoft.restaurant.service;

import org.springframework.stereotype.Service;
import peaksoft.restaurant.dto.SimpleResponce;
import peaksoft.restaurant.dto.restDto.request.RestaurantRequest;
import peaksoft.restaurant.dto.restDto.response.RestaurantResponce;

import java.util.List;

@Service
public interface RestaurantService {
    SimpleResponce save(RestaurantRequest restaurantRequest);
    RestaurantResponce findById(Long id);
    List<RestaurantResponce> findAll();
    SimpleResponce update(Long id,RestaurantRequest restaurantRequest);
    SimpleResponce delete(Long id);

}
