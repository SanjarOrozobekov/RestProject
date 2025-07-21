package peaksoft.restaurant.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "menuitems")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "menuitem_gen")
    @SequenceGenerator(name = "menuitem_gen",sequenceName = "menuitem_seq",allocationSize = 1)
    Long id;
    String name;
    String image;
    int price;
    String description;
    boolean isVegetarian;
    @ManyToOne
    Restaurant restaurant;
    @ManyToMany(mappedBy = "menuItems")
    List<Cheque> cheques;
    @OneToOne
    StopList stopList;
    @ManyToOne
    Subcategory subcategory;
}
