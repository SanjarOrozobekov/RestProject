package peaksoft.restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.restaurant.dto.SimpleResponce;
import peaksoft.restaurant.dto.userDTO.request.UserRequest;
import peaksoft.restaurant.service.UserService;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserAPI {
    private final UserService userService;

    @PostMapping("/{adminId}/{restaurantId}")
    public SimpleResponce saveEmployeeByAdmin(@RequestBody UserRequest userRequest, @PathVariable Long adminId, @PathVariable Long restaurantId) {
        return userService.saveEmployeeByAdmin(userRequest,adminId,restaurantId);
    }
}
