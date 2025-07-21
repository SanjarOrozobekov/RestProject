package peaksoft.restaurant.dto.chequeDTO.request;

import java.util.List;

public record ChegueRequest (
        Long userId,
        List<Long> menuItemIds
){
}
