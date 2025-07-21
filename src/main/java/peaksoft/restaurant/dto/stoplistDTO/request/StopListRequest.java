package peaksoft.restaurant.dto.stoplistDTO.request;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record StopListRequest (
        String reason,
        LocalDateTime date,
        Long menuItemId
){
}
