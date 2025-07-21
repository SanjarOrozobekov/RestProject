package peaksoft.restaurant.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.restaurant.dto.SimpleResponce;
import peaksoft.restaurant.dto.chequeDTO.request.ChegueRequest;
import peaksoft.restaurant.dto.chequeDTO.request.ChegueRequestUpdate;
import peaksoft.restaurant.dto.chequeDTO.responce.ChegueResponce;
import peaksoft.restaurant.entities.Cheque;
import peaksoft.restaurant.entities.MenuItem;
import peaksoft.restaurant.entities.User;
import peaksoft.restaurant.enums.Role;
import peaksoft.restaurant.repo.ChequeRepo;
import peaksoft.restaurant.repo.MenuItemRepo;
import peaksoft.restaurant.repo.UserRepo;
import peaksoft.restaurant.service.ChequeService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepo chequeRepo;
    private final UserRepo userRepo;
    private final MenuItemRepo menuItemsRepo;

    @Override
    public SimpleResponce save(Long requestOwnerId, ChegueRequest chequeRequest) {
        User requestOwner = userRepo.findById(requestOwnerId).orElseThrow(
                () -> new NullPointerException(String.format("User with id %s not found", requestOwnerId))
        );

        if (requestOwner.getRole() == Role.CHEF) {
            return SimpleResponce.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("FORBIDDEN")
                    .build();
        }

        User user = userRepo.findById(chequeRequest.userId()).orElseThrow(
                () -> new NullPointerException(String.format("User with id %s not found", chequeRequest.userId()))
        );

        int totalPrice = 0, restaurantService = 0, loopCount = 0;

        Cheque cheque = new Cheque();

        for (Long menuItemId : chequeRequest.menuItemIds()) {
            MenuItem menuItem = menuItemsRepo.findById(menuItemId).orElseThrow(
                    () -> new NullPointerException(String.format("Menu item with id %s not found", menuItemId))
            );
            loopCount++;

            totalPrice = totalPrice + menuItem.getPrice();
            restaurantService = menuItem.getRestaurant().getService();
            cheque.getMenuItems().add(menuItem);
        }

        cheque.setUser(user);
        cheque.setService(restaurantService);
        cheque.setGrandTotal(totalPrice);
        cheque.setDate(LocalDateTime.now());
        cheque.setPriceAverage((double) totalPrice /loopCount);

        if (!Objects.equals(user.getRestaurant().getId(), cheque.getMenuItems().getFirst().getRestaurant().getId())) {
            return SimpleResponce.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("FORBIDDEN")
                    .build();
        }

        chequeRepo.save(cheque);

        return SimpleResponce.builder()
                .status(HttpStatus.CREATED)
                .message("Cheque saved successfully")
                .build();
    }

    @Override
    public ChegueResponce getById(Long id) {
//        Long id;
//        double priceAverage;
//        LocalDateTime createdAt;
//        int Service;
//        int grandTotal;
//        List<MenuItem> menuItems;

        Cheque cheque = chequeRepo.findById(id).orElseThrow(
                () -> new NullPointerException(String.format("Cheque with id %s not found", id))
        );
        return ChegueResponce.builder()
                .id(cheque.getId())
                .priceAverage(cheque.getPriceAverage())
                .createdAt(cheque.getDate())
                .service(cheque.getService())
                .grandTotal(cheque.getGrandTotal())
                .build();
    }

    @Override
    public List<ChegueResponce> getAll() {
        return chequeRepo.getAll();
    }

    @Override
    public SimpleResponce update(Long id, Long requestOwnerId, ChegueRequestUpdate chequeRequestUpdate) {
        User user = userRepo.findById(requestOwnerId).orElseThrow(
                () -> new NullPointerException(String.format("User with id %s not found", requestOwnerId))
        );
        if (user.getRole() == Role.WAITER || user.getRole() == Role.CHEF) {
            return SimpleResponce.builder()
                    .status(HttpStatus.FORBIDDEN)
                    .message("You dont change the cheque")
                    .build();
        }

        User userForUpdate = userRepo.findById(chequeRequestUpdate.userId()).orElseThrow(
                () -> new NullPointerException(String.format("User with id %s not found", chequeRequestUpdate.userId()))
        );

        Cheque cheque = chequeRepo.findById(id).orElseThrow(
                () -> new NullPointerException(String.format("Cheque with id %s not found", id))
        );
        cheque.setUser(userForUpdate);
        chequeRepo.save(cheque);

        return SimpleResponce.builder()
                .status(HttpStatus.OK)
                .message("Cheque updated successfully")
                .build();
    }

    @Override
    @Transactional
    public SimpleResponce delete(Long id, Long requestOwnerId) {
        User requestOwner = userRepo.findById(requestOwnerId).orElseThrow(
                () -> new NullPointerException(String.format("User with id %s not found", requestOwnerId))
        );
        if (requestOwner.getRole() == Role.WAITER || requestOwner.getRole() == Role.CHEF) {
            return SimpleResponce.builder()
                    .status(HttpStatus.FORBIDDEN)
                    .message("You dont change the cheque")
                    .build();
        }

        Cheque cheque = chequeRepo.findById(id).orElseThrow(
                () -> new NullPointerException(String.format("Cheque with id %s not found", id))
        );

        cheque.setUser(null);
        cheque.setMenuItems(null);
        chequeRepo.delete(cheque);

        return SimpleResponce.builder()
                .status(HttpStatus.OK)
                .message("Cheque deleted successfully")
                .build();
    }

    @Override
    public List<ChegueResponce> getAllChequesByUserId(Long userId) {
        return chequeRepo.getAllChequesByUserId(userId);
    }

    @Override
    public double getAvgPriceByRestaurantIdInTheDay(Long requestOwnerId, Long restaurantId, LocalDate localDate) {
        List<Cheque> cheques = chequeRepo.getAllChequesByRestaurantId(restaurantId);
        int sum = 0;
        int count = 0;
        for (Cheque cheque : cheques) {
            if (cheque.getDate().toLocalDate().equals(localDate)) {
                count++;
                sum = sum + cheque.getGrandTotal();
            }
        }
        return (double) sum / count;
    }

    @Override
    public double getTotalPriceByUserIdInTheDay(Long userId, LocalDate localDate) {
        User user = userRepo.findById(userId).orElseThrow(
                () -> new NullPointerException(String.format("User with id %s not found", userId))
        );
        int c = 0, res = 0;

        for (Cheque cheque : user.getCheques()) {
            if (cheque.getDate().toLocalDate().equals(localDate)) {
                res = res + cheque.getGrandTotal();
                c++;
            }
        }
        return (double) res / c;
    }
}
