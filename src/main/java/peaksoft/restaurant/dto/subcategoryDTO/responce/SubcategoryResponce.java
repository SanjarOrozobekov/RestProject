package peaksoft.restaurant.dto.subcategoryDTO.responce;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class SubcategoryResponce {
    Long id;
    String name;

    public SubcategoryResponce(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
