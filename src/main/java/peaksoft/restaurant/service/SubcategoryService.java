package peaksoft.restaurant.service;


import org.springframework.stereotype.Service;
import peaksoft.restaurant.dto.SimpleResponce;
import peaksoft.restaurant.dto.categoryDTO.request.CategoryRequest;
import peaksoft.restaurant.dto.subcategoryDTO.request.SubcategoryRequest;
import peaksoft.restaurant.dto.subcategoryDTO.responce.SubcategoryResponce;
import peaksoft.restaurant.entities.Subcategory;

import java.util.List;

@Service
public interface SubcategoryService {
    SimpleResponce saveInCategory(Long categoryId,SubcategoryRequest subcategoryRequest);
    SubcategoryResponce findById(Long id);
    List<SubcategoryResponce> findAll();
    SimpleResponce update(Long id,SubcategoryRequest subcategoryRequest);
    SimpleResponce delete(Long id);
    List<Subcategory> findByCategoryIdOrderByNameAsc(Long categoryId);
    List<Subcategory> findAllByOrderByCategoryNameAscNameAsc();
}
