package peaksoft.restaurant.service;

import org.springframework.stereotype.Service;
import peaksoft.restaurant.dto.SimpleResponce;
import peaksoft.restaurant.dto.chequeDTO.request.ChegueRequest;
import peaksoft.restaurant.dto.chequeDTO.request.ChegueRequestUpdate;
import peaksoft.restaurant.dto.chequeDTO.responce.ChegueResponce;

import java.time.LocalDate;
import java.util.List;

@Service
public interface ChequeService {
    SimpleResponce save (Long userId, ChegueRequest chequeRequest);
    ChegueResponce getById(Long id);
    List<ChegueResponce> getAll();
    SimpleResponce update (Long id, Long requestOwnerId, ChegueRequestUpdate chequeRequestUpdate);
    SimpleResponce delete (Long id, Long requestOwnerId);
    List<ChegueResponce> getAllChequesByUserId(Long userId);
    double getAvgPriceByRestaurantIdInTheDay (Long requestOwnerId, Long restaurantId, LocalDate localDate);
    double getTotalPriceByUserIdInTheDay (Long userId, LocalDate localDate);
}
