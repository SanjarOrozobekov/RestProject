package peaksoft.restaurant.dto.userDTO.responce;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import peaksoft.restaurant.enums.Role;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
public class Userresponce {
    Long id;
    String firstName;
    String lastName;
    LocalDate dateOfBirth;
    String email;
    String phoneNumber;
    Role role;
    int experience;
    boolean status;

    public Userresponce(Long id,String fullName, String lastName, LocalDate dateOfBirth, String email, String phoneNumber, Role role, int experience, boolean status) {
        this.id = id;
        this.firstName = fullName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.experience = experience;
        this.status = status;

    }
}
