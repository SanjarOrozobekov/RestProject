package peaksoft.restaurant.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.restaurant.dto.SimpleResponce;
import peaksoft.restaurant.dto.stoplistDTO.request.StopListRequest;
import peaksoft.restaurant.dto.stoplistDTO.responce.StoplistResponce;
import peaksoft.restaurant.entities.MenuItem;
import peaksoft.restaurant.entities.StopList;
import peaksoft.restaurant.entities.User;
import peaksoft.restaurant.enums.Role;
import peaksoft.restaurant.repo.MenuItemRepo;
import peaksoft.restaurant.repo.StoplistRepo;
import peaksoft.restaurant.repo.UserRepo;
import peaksoft.restaurant.service.StopListService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StopListServiceImpl implements StopListService {

    private final StoplistRepo stoplistRepo;
    private final UserRepo userRepo;
    private final MenuItemRepo menuItemRepo;

    @Override
    public SimpleResponce save(Long userId, Long menuItemId, StopListRequest stopListRequest) {
        User user = userRepo.findById(userId)
                .orElseThrow(()->new NullPointerException("User not found"));
        if(!(user.getRole() == Role.ADMIN || user.getRole() == Role.CHEF)) {
            return new SimpleResponce(HttpStatus.FORBIDDEN,"Only ADMIN or CHEF role allowed");
        }
        MenuItem menuItem = menuItemRepo.findById(stopListRequest.menuItemId())
                .orElseThrow(() -> new RuntimeException("MenuItem not found"));
        boolean exists = stoplistRepo.existsByDateAndMenuItemId(stopListRequest.date(), stopListRequest.menuItemId()).isPresent();
        if (exists) {
            return new SimpleResponce(HttpStatus.NOT_FOUND,"StopList already exists for this menu item on this date!");
        }
        StopList stopList = new StopList();
        stopList.setReason(stopListRequest.reason());
        stopList.setDate(stopListRequest.date());
        stopList.setMenuItem(menuItem);
        stoplistRepo.save(stopList);
        return new SimpleResponce(HttpStatus.OK,"StopList has been saved successfully!");
    }

    @Override
    public SimpleResponce update(Long id, StopListRequest stopListRequest) {
        StopList stopList = stoplistRepo.findById(id).orElseThrow(
                () -> new NullPointerException(String.format("StopList with id %s not found", id))
        );

        stopList.setReason(stopListRequest.reason());
        stoplistRepo.save(stopList);
        return SimpleResponce.builder()
                .status(HttpStatus.OK)
                .message("Stop list successfully updated")
                .build();
    }

    @Override
    public StoplistResponce findById(Long id) {
        StopList stopList = stoplistRepo.findById(id).orElseThrow(
                () -> new NullPointerException(String.format("StopList with id %s not found", id))
        );

        return StoplistResponce.builder()
                .id(stopList.getId())
                .date(stopList.getDate())
                .build();
    }

    @Override
    public List<StoplistResponce> findAll() {
        return List.of();
    }

    @Override
    public SimpleResponce delete(Long id) {
        StopList stopList = stoplistRepo.findById(id).orElseThrow(
                () -> new NullPointerException(String.format("StopList with id %s not found", id))
        );

        MenuItem menuItem = stopList.getMenuItem();
        menuItem.setStopList(null);
        stopList.setMenuItem(null);
        stoplistRepo.delete(stopList);

        return SimpleResponce.builder()
                .status(HttpStatus.OK)
                .message("Stop list successfully deleted")
                .build();
    }
}
