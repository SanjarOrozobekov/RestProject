package peaksoft.restaurant.service;

import org.springframework.stereotype.Service;
import peaksoft.restaurant.dto.SimpleResponce;
import peaksoft.restaurant.dto.stoplistDTO.request.StopListRequest;
import peaksoft.restaurant.dto.stoplistDTO.responce.StoplistResponce;

import java.util.List;

@Service
public interface StopListService {
    SimpleResponce save (Long userId, Long menuItemId, StopListRequest stopListRequest);
    StoplistResponce findById (Long id);
    List<StoplistResponce> findAll();
    SimpleResponce update (Long id, StopListRequest stopListRequest);
    SimpleResponce delete (Long id);
}
