package peaksoft.restaurant.init;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import peaksoft.restaurant.entities.User;
import peaksoft.restaurant.enums.Role;
import peaksoft.restaurant.repo.UserRepo;
import peaksoft.restaurant.service.UserService;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class Admin {

    private final UserRepo userRepo;

    @PostConstruct
    public void init(){
        User admin = User.builder()
                .firstName("Sanjar")
                .lastName("Orozobekov")
                .dateOfBirth(LocalDate.of(2006, 11, 15))
                .email("sake@gmail.com")
                .password("12345")
                .phoneNumber("+996500061511")
                .role(Role.ADMIN)
                .status(true)
                .experience(5)
                .build();
        userRepo.save(admin);
    }
}
