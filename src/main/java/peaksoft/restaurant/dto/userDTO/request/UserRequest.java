package peaksoft.restaurant.dto.userDTO.request;

import jakarta.persistence.Column;
import peaksoft.restaurant.enums.Role;

import java.time.LocalDate;

public record UserRequest (
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        String email,
        String password,
        String phoneNumber,
        Role role,
        int experience,
        boolean status
){
}
