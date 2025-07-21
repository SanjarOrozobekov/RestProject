package peaksoft.restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import peaksoft.restaurant.dto.SimpleResponce;
import peaksoft.restaurant.dto.stoplistDTO.request.StopListRequest;
import peaksoft.restaurant.dto.stoplistDTO.responce.StoplistResponce;
import peaksoft.restaurant.service.StopListService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/stopLists")
public class StopListAPI {
    private final StopListService stopListService;

    // ✅ Create
    @PostMapping
    public ResponseEntity<SimpleResponce> save(
            @RequestParam Long userId,
            @RequestParam Long menuItemId,
            @RequestBody StopListRequest stopListRequest
    ) {
        SimpleResponce response = stopListService.save(userId, menuItemId, stopListRequest);
        return new ResponseEntity<>(response, response.getStatus());
    }

    // ✅ Get All
    @GetMapping
    public List<StoplistResponce> getAll() {
        return stopListService.findAll();
    }

    // ✅ Get by ID
    @GetMapping("/{id}")
    public StoplistResponce getById(@PathVariable Long id) {
        return stopListService.findById(id);
    }

    // ✅ Update
    @PutMapping("/{id}")
    public ResponseEntity<SimpleResponce> update(
            @PathVariable Long id,
            @RequestBody StopListRequest stopListRequest
    ) {
        SimpleResponce response = stopListService.update(id, stopListRequest);
        return new ResponseEntity<>(response, response.getStatus());
    }

    // ✅ Delete
    @DeleteMapping("/{id}")
    public SimpleResponce delete(@PathVariable Long id) {
        return stopListService.delete(id);
    }
}
