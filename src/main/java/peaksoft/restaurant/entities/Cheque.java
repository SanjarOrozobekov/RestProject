package peaksoft.restaurant.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "chegues")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cheque {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "chegue_gen")
    @SequenceGenerator(name = "chegue_gen",sequenceName = "chegue_seq",allocationSize = 1)
    Long id;
    double priceAverage;
    LocalDateTime date;
    int service;
    int grandTotal;
    @ManyToOne(cascade = CascadeType.DETACH)
    User user;
    @ManyToMany(cascade = CascadeType.DETACH)
    List<MenuItem> menuItems;


}
