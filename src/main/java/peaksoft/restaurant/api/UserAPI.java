package peaksoft.restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import peaksoft.restaurant.dto.SimpleResponce;
import peaksoft.restaurant.dto.userDTO.request.UserRequest;
import peaksoft.restaurant.dto.userDTO.responce.Userresponce;
import peaksoft.restaurant.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserAPI {
    private final UserService userService;

    @PostMapping("/{adminId}/{restaurantId}")
    public SimpleResponce saveEmployeeByAdmin(@RequestBody UserRequest userRequest, @PathVariable Long adminId, @PathVariable Long restaurantId) {
        return userService.saveEmployeeByAdmin(userRequest,adminId,restaurantId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Userresponce> getUserById(@PathVariable Long id) {
        Userresponce user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<List<Userresponce>> getAllUsers() {
        List<Userresponce> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SimpleResponce> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequest userRequest
    ) {
        SimpleResponce response = userService.update(id, userRequest);
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SimpleResponce> deleteUser(@PathVariable Long id) {
        SimpleResponce response = userService.delete(id);
        return ResponseEntity.status(response.getStatus()).body(response);
    }
}
