package peaksoft.restaurant.service;

import org.springframework.stereotype.Service;
import peaksoft.restaurant.dto.SimpleResponce;
import peaksoft.restaurant.dto.userDTO.request.UserRequest;
import peaksoft.restaurant.dto.userDTO.responce.Userresponce;

import java.util.List;

@Service
public interface UserService {
    SimpleResponce saveEmployeeByAdmin(UserRequest userRequest,Long adminId,Long restaurantId);
    Userresponce findById(Long id);
    List<Userresponce> findAll();
    SimpleResponce update(Long id,UserRequest userRequest);
    SimpleResponce delete(Long id);
}
