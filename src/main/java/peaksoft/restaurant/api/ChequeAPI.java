package peaksoft.restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import peaksoft.restaurant.dto.SimpleResponce;
import peaksoft.restaurant.dto.chequeDTO.request.ChegueRequest;
import peaksoft.restaurant.dto.chequeDTO.request.ChegueRequestUpdate;
import peaksoft.restaurant.dto.chequeDTO.responce.ChegueResponce;
import peaksoft.restaurant.service.ChequeService;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/cheques")
public class ChequeAPI {
    private final ChequeService chequeService;

    @PostMapping("/{requestOwnerId}")
    public SimpleResponce save(@PathVariable Long requestOwnerId,
                               @RequestBody ChegueRequest chequeRequest) {
        return chequeService.save(requestOwnerId, chequeRequest);
    }

    @GetMapping("/{id}")
    public ChegueResponce getChequeById(@PathVariable Long id) {
        return chequeService.getById(id);
    }

    @GetMapping
    public List<ChegueResponce> getAllCheques() {
        return chequeService.getAll();
    }

    @PutMapping("/{id}")
    public SimpleResponce updateCheque(@PathVariable Long id,
                                       @RequestParam Long requestOwnerId,
                                       @RequestBody ChegueRequestUpdate chequeRequestUpdate) {
        return chequeService.update(id, requestOwnerId, chequeRequestUpdate);
    }

    @DeleteMapping("/{id}")
    public SimpleResponce deleteCheque(@PathVariable Long id,
                                       @RequestParam Long requestOwnerId) {
        return chequeService.delete(id, requestOwnerId);
    }

    @GetMapping("/user/{userId}")
    public List<ChegueResponce> getChequesByUserId(@PathVariable Long userId) {
        return chequeService.getAllChequesByUserId(userId);
    }

    @GetMapping("/average")
    public double getAveragePriceByRestaurantId(@RequestParam Long requestOwnerId,
                                                @RequestParam Long restaurantId,
                                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return chequeService.getAvgPriceByRestaurantIdInTheDay(requestOwnerId, restaurantId, date);
    }

    @GetMapping("/total")
    public double getTotalPriceByUserAndDate(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return chequeService.getTotalPriceByUserIdInTheDay(userId, date);
    }
}
