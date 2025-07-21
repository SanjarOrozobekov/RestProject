package peaksoft.restaurant.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import peaksoft.restaurant.enums.RestType;

import java.util.List;

@Entity
@Table(name = "restaurants")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "restaurant_gen")
    @SequenceGenerator(name = "restaurant_gen",sequenceName = "restaurant_seq",allocationSize = 1)
    Long id;
    String name;
    String location;
    RestType restType;
    int numberOfEmployees = 0;
    int service;
    @OneToMany(mappedBy = "restaurant")
    List<User> users;
    @OneToMany(mappedBy = "restaurant")
    List<MenuItem> menuItems;
}
