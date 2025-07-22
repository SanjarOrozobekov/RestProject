package peaksoft.restaurant.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import peaksoft.restaurant.dto.SimpleResponce;
import peaksoft.restaurant.dto.subcategoryDTO.request.SubcategoryRequest;
import peaksoft.restaurant.dto.subcategoryDTO.responce.SubcategoryResponce;
import peaksoft.restaurant.entities.Category;
import peaksoft.restaurant.entities.MenuItem;
import peaksoft.restaurant.entities.Subcategory;
import peaksoft.restaurant.repo.CategoryRepo;
import peaksoft.restaurant.repo.MenuItemRepo;
import peaksoft.restaurant.repo.SubcategoryRepo;
import peaksoft.restaurant.service.SubcategoryService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepo subcategoryRepo;
    private final CategoryRepo categoryRepo;
    private final MenuItemRepo menuItemRepo;

    @Override
    public SimpleResponce saveInCategory(Long categoryId,SubcategoryRequest subcategoryRequest) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new RuntimeException("Category Not Found"));
        Subcategory subcategory = new Subcategory();
        subcategory.setName(subcategoryRequest.name());
        subcategory.setCategory(category);
        //category.getSubcategories().add(subcategory);
        subcategoryRepo.save(subcategory);
        categoryRepo.save(category);
        return SimpleResponce.builder()
                .status(HttpStatus.OK)
                .message("Subcategory Saved Successfully")
                .build();
    }

    @Override
    public SubcategoryResponce findById(Long id) {
        Subcategory subcategory = subcategoryRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Subcategory Not Found"));
        return SubcategoryResponce.builder()
                .id(subcategory.getId())
                .name(subcategory.getName())
                .build();
    }

    @Override
    public List<SubcategoryResponce> findAll() {
        List<Subcategory> subcategories = subcategoryRepo.findAll();
        List<SubcategoryResponce> subcategoryResponceList = new ArrayList<>();

        subcategories.forEach(subcategory -> {
            SubcategoryResponce subcategoryResponce = new SubcategoryResponce();
            subcategoryResponce.setId(subcategory.getId());
            subcategoryResponce.setName(subcategory.getName());
            subcategoryResponceList.add(subcategoryResponce);
        });
        return subcategoryResponceList;
    }

    @Override
    public SimpleResponce update(Long id, SubcategoryRequest subcategoryRequest) {
        Subcategory subcategory = subcategoryRepo.findById(id)
                .orElseThrow(()-> new RuntimeException("Subcategory Not Found"));
        subcategory.setName(subcategoryRequest.name());
        subcategoryRepo.save(subcategory);
        return SimpleResponce.builder()
                .status(HttpStatus.OK)
                .message("Subcategory Updated Successfully")
                .build();
    }

    @Override
    public SimpleResponce delete(Long id) {
        Subcategory subCategory = subcategoryRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "SubCategory not found"));

        Category category = subCategory.getCategory();
        if (category != null) {
            category.getSubcategories().remove(subCategory);
            subCategory.setCategory(null);
        }

        try {
            if (subCategory.getMenuItems() != null) {
                for (MenuItem menuItem : subCategory.getMenuItems()) {
                    //menuItemRepo.deleteById(menuItem.getId());
                    menuItem.setSubcategory(null);
                }
                subCategory.getMenuItems().clear();
            }
        } catch (Exception e) {
            throw new RuntimeException("subCategory delete method - "+e.getMessage());
        }

        subcategoryRepo.delete(subCategory);

        return SimpleResponce.builder()
                .status(HttpStatus.OK)
                .message("SubCategory deleted successfully")
                .build();
    }

    @Override
    public List<Subcategory> findByCategoryIdOrderByNameAsc(Long categoryId) {
        return subcategoryRepo.findByCategoryIdOrderByNameAsc(categoryId);
    }

    @Override
    public List<Subcategory> findAllByOrderByCategoryNameAscNameAsc() {
        return subcategoryRepo.findAllByOrderByCategoryNameAscNameAsc();
    }
}
