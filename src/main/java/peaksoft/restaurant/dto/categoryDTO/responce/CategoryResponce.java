package peaksoft.restaurant.dto.categoryDTO.responce;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class CategoryResponce {
    Long id;
    String name;

    public CategoryResponce(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
