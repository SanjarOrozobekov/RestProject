package peaksoft.restaurant.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "stoplists")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StopList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "stoplist_gen")
    @SequenceGenerator(name = "stoplist_gen",sequenceName = "stoplist_seq",allocationSize = 1)
    Long id;
    String reason;
    LocalDateTime date;
    @OneToOne(mappedBy = "stopList")
    MenuItem menuItem;
}
