//package peaksoft.restaurant.api;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import peaksoft.restaurant.service.UserService;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthApi {
//    private final UserService userService;
//
//    public AuthApi(UserService userService) {
//        this.userService = userService;
//    }
//
//    @PostMapping("/login")
//    public AuthResponse login(@RequestBody LoginRequest loginRequest) {
//        return userService.signIn(loginRequest);
//    }
//
//    @PostMapping("/register")
//    public AuthResponse register(@RequestBody RegisterRequest registerRequest) {
//        return userService.register(registerRequest);
//    }
//}
