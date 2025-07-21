package peaksoft.restaurant.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import peaksoft.restaurant.dto.SimpleResponce;
import peaksoft.restaurant.dto.userDTO.request.UserRequest;
import peaksoft.restaurant.dto.userDTO.responce.Userresponce;
import peaksoft.restaurant.entities.Restaurant;
import peaksoft.restaurant.entities.User;
import peaksoft.restaurant.enums.Role;
import peaksoft.restaurant.repo.RestaurantRepo;
import peaksoft.restaurant.repo.UserRepo;
import peaksoft.restaurant.service.UserService;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final RestaurantRepo restaurantRepo;
    @Override
    public SimpleResponce saveEmployeeByAdmin(UserRequest userRequest,Long adminId,Long restaurantId) {
      User admin = userRepo.findById(adminId)
              .orElseThrow(()->new EntityNotFoundException("Admin not found"));
      if(admin.getRole() !=Role.ADMIN){
          throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only admins can create employees");
      }
      if(userRepo.existsByEmail(userRequest.email())){
          throw new IllegalArgumentException("Email already exists");
      }
      if (userRequest.password().length() < 4){
          throw new RuntimeException("Password too short");
      }
        LocalDate birthday = userRequest.dateOfBirth();
      int age = Period.between(birthday,LocalDate.now()).getYears();
      if(userRequest.role() == Role.CHEF && age < 25 || userRequest.role() == Role.CHEF && age > 45){
          throw new RuntimeException("The chef must be between 25 and 45 years old");
      }
        if(userRequest.role() == Role.WAITER && age < 18 || userRequest.role() == Role.WAITER && age > 30){
            throw new RuntimeException("The waiter must be between 18 and 30 years old");
        }

        if (userRequest.role() == Role.WAITER && userRequest.experience() < 1){
            throw new RuntimeException("Must have more than 1 year of experience.");
        }

        if (userRequest.role() == Role.CHEF && userRequest.experience() < 2){
            throw new RuntimeException("Must have more than 2 years of experience.");
        }

        if(!userRequest.phoneNumber().matches("^\\+996\\d{9}$")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number is invalid");
        }



      Restaurant restaurant = restaurantRepo.findById(restaurantId)
              .orElseThrow(()->new EntityNotFoundException("Restaurant not found"));
        if (restaurant.getNumberOfEmployees() >= 15){
            return SimpleResponce.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Maximum number of employees in restaurant (15)")
                    .build();
        }
        restaurant.setNumberOfEmployees(restaurant.getNumberOfEmployees() + 1);

      User employee = User.builder()
              .firstName(userRequest.firstName())
              .lastName(userRequest.lastName())
              .dateOfBirth(userRequest.dateOfBirth())
              .email(userRequest.email())
              .password(userRequest.password())
              .phoneNumber(userRequest.phoneNumber())
              .role(userRequest.role())
              .experience(userRequest.experience())
              .status(userRequest.status())
              .build();
      employee.setRestaurant(restaurant);

      userRepo.save(employee);
        return SimpleResponce.builder()
                .status(HttpStatus.OK)
                .message("Employee created successfully")
                .build();
    }

    @Override
    public Userresponce findById(Long id) {
        User user = userRepo.findById(id).orElseThrow(
                () -> new NullPointerException(String.format("user with id %s not found", id))
        );

        return Userresponce.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .dateOfBirth(user.getDateOfBirth())
                .role(user.getRole())
                .experience(user.getExperience())
                .build();
    }

    @Override
    public List<Userresponce> findAll() {
        List<User> users = userRepo.findAll();
        List<Userresponce> userResponceList = new ArrayList<>();
        users.forEach(user -> {
            Userresponce userresponce = new Userresponce();
            userresponce.setId(user.getId());
            userresponce.setFirstName(user.getFirstName());
            userresponce.setLastName(user.getLastName());
            userresponce.setEmail(user.getEmail());
            userresponce.setPhoneNumber(user.getPhoneNumber());
            userresponce.setDateOfBirth(user.getDateOfBirth());
            userresponce.setRole(user.getRole());
            userresponce.setExperience(user.getExperience());
            userResponceList.add(userresponce);
        });
        return userResponceList;
    }

    @Override
    public SimpleResponce update(Long id, UserRequest userRequest) {
        User user = userRepo.findById(id).orElseThrow(
                () -> new NullPointerException(String.format("user with id %s not found", id))
        );

        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setEmail(userRequest.email());
        user.setPhoneNumber(userRequest.phoneNumber());
        user.setPassword(userRequest.password());
        user.setRole(userRequest.role());
        user.setExperience(userRequest.experience());

        userRepo.save(user);
        return SimpleResponce.builder()
                .status(HttpStatus.OK)
                .message("user updated")
                .build();
    }

    @Override
    @Transactional
    public SimpleResponce delete(Long id) {
        User user = userRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
        );
        Restaurant restaurant = user.getRestaurant();
        List<User> users = restaurant.getUsers();
        users.remove(user);
        user.setRestaurant(null);

        user.getCheques()
                .forEach(cheque -> cheque.setUser(null));

        userRepo.delete(user);
        return SimpleResponce.builder()
                .status(HttpStatus.OK)
                .message("user deleted")
                .build();
    }
}
