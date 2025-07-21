package peaksoft.restaurant.dto.restDto.request;

import peaksoft.restaurant.enums.RestType;

public record RestaurantRequest (
        String name,
        String location,
        RestType restType,
        int service
){
}
