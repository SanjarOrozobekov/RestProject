package peaksoft.restaurant.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import peaksoft.restaurant.dto.SimpleResponce;
import peaksoft.restaurant.dto.subcategoryDTO.request.SubcategoryRequest;
import peaksoft.restaurant.dto.subcategoryDTO.responce.SubcategoryResponce;
import peaksoft.restaurant.entities.Subcategory;
import peaksoft.restaurant.service.SubcategoryService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/subcategories")
public class SubcategoryAPI {
    private final SubcategoryService subcategoryService;

    @PostMapping("/{categoryId}")
    public SimpleResponce saveSubCategory(@PathVariable Long categoryId,
                                          @RequestBody SubcategoryRequest subCategoryRequest) {
        return subcategoryService.saveInCategory(categoryId, subCategoryRequest);
    }

    @GetMapping("/{id}")
    public SubcategoryResponce getSubCategoryById(@PathVariable Long id) {
        return subcategoryService.findById(id);
    }

    // GET /api/subcategories
    @GetMapping
    public List<SubcategoryResponce> getAllSubCategories() {
        return subcategoryService.findAll();
    }

    // PUT /api/subcategories/{id}
    @PutMapping("/{id}")
    public SimpleResponce updateSubCategoryById(@PathVariable Long id,
                                                @RequestBody SubcategoryRequest subCategoryRequest) {
        return subcategoryService.update(id, subCategoryRequest);
    }

    @DeleteMapping("/{id}")
    public SimpleResponce delete (@PathVariable Long id) {
        return subcategoryService.delete(id);
    }

    @GetMapping("/byCategoryId/{id}")
    public List<Subcategory> getAllByCategoryId (@PathVariable Long id) {
        return subcategoryService.findByCategoryIdOrderByNameAsc(id);
    }
}
