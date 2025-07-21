package peaksoft.restaurant.service;

import org.springframework.stereotype.Service;
import peaksoft.restaurant.dto.SimpleResponce;
import peaksoft.restaurant.dto.categoryDTO.request.CategoryRequest;
import peaksoft.restaurant.dto.categoryDTO.responce.CategoryResponce;
import peaksoft.restaurant.dto.userDTO.request.UserRequest;

import java.util.List;

@Service
public interface CategorySrevice {
    SimpleResponce save(CategoryRequest categoryRequest);
    CategoryResponce findById(Long id);
    List<CategoryResponce> findAll();
    SimpleResponce update(Long id,CategoryRequest categoryRequest);
    SimpleResponce delete(Long id);
}
