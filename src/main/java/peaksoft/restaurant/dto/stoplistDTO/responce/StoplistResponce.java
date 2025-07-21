package peaksoft.restaurant.dto.stoplistDTO.responce;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Builder
public class StoplistResponce {
    Long id;
    String reason;
    LocalDateTime date;
    Long menuItemId;

    public StoplistResponce(Long id, String reason, LocalDateTime date, Long menuItemId) {
        this.id = id;
        this.reason = reason;
        this.date = date;
        this.menuItemId = menuItemId;
    }
}
