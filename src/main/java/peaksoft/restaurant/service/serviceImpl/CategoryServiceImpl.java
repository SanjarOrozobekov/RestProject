package peaksoft.restaurant.service.serviceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import peaksoft.restaurant.dto.SimpleResponce;
import peaksoft.restaurant.dto.categoryDTO.request.CategoryRequest;
import peaksoft.restaurant.dto.categoryDTO.responce.CategoryResponce;
import peaksoft.restaurant.entities.Category;
import peaksoft.restaurant.repo.CategoryRepo;
import peaksoft.restaurant.service.CategorySrevice;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategorySrevice {

    private final CategoryRepo categoryRepo;

    @Override
    public SimpleResponce save(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.name());
        categoryRepo.save(category);
        return SimpleResponce.builder()
                .status(HttpStatus.OK)
                .message("Category Saved Successfully")
                .build();
    }

    @Override
    public CategoryResponce findById(Long id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(()-> new NullPointerException("Category Not Found"));
        return CategoryResponce.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    @Override
    public List<CategoryResponce> findAll() {
        List<Category> categories = categoryRepo.findAll();
        List<CategoryResponce> categoryResponceList = new ArrayList<>();
        categories.forEach(category -> {
            CategoryResponce categoryResponce = new CategoryResponce();
            categoryResponce.setId(category.getId());
            categoryResponce.setName(category.getName());
            categoryResponceList.add(categoryResponce);
        });
        return categoryResponceList;
    }

    @Override
    public SimpleResponce update(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(()-> new NullPointerException("Category Not Found"));
        category.setName(categoryRequest.name());
        categoryRepo.save(category);
        return SimpleResponce.builder()
                .status(HttpStatus.OK)
                .message("Category Updated Successfully")
                .build();
    }

    @Override
    public SimpleResponce delete(Long id) {
        Category category = categoryRepo.findById(id)
                .orElseThrow(()-> new NullPointerException("Category Not Found"));
        categoryRepo.delete(category);
        return SimpleResponce.builder()
                .status(HttpStatus.OK)
                .message("Category Deleted Successfully")
                .build();
    }
}
