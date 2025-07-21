package peaksoft.restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.restaurant.dto.SimpleResponce;
import peaksoft.restaurant.dto.categoryDTO.request.CategoryRequest;
import peaksoft.restaurant.dto.categoryDTO.responce.CategoryResponce;
import peaksoft.restaurant.service.CategorySrevice;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryAPI {

    private final CategorySrevice categorySrevice;

    @PostMapping
    public SimpleResponce save(@RequestBody CategoryRequest categoryRequest) {
        return categorySrevice.save(categoryRequest);
    }

    @GetMapping("/{id}")
    public CategoryResponce findById(@PathVariable Long id) {
        return categorySrevice.findById(id);
    }

    @GetMapping
    public List<CategoryResponce> findAll() {
        return categorySrevice.findAll();
    }

    @PutMapping("/{id}")
    public SimpleResponce update(@PathVariable Long id,@RequestBody CategoryRequest categoryRequest) {
        return categorySrevice.update(id, categoryRequest);
    }

    @DeleteMapping("/{id}")
    public SimpleResponce delete(@PathVariable Long id) {
        return categorySrevice.delete(id);
    }
}
