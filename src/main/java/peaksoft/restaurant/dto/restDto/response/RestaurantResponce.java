package peaksoft.restaurant.dto.restDto.response;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import peaksoft.restaurant.enums.RestType;

@Data
@Builder
@NoArgsConstructor
public class RestaurantResponce {
    Long id;
    String name;
    String location;
    RestType restType;
    int numberOfEmployees = 0;
    int service;

    public RestaurantResponce(Long id, String name, String location, RestType restType, int numberOfEmployees, int service) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.restType = restType;
        this.numberOfEmployees = numberOfEmployees;
        this.service = service;
    }
}
