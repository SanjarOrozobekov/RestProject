package peaksoft.restaurant.dto.chequeDTO.responce;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
public class ChegueResponce {
    Long id;
    double priceAverage;
    LocalDateTime createdAt;
    int service;
    int grandTotal;

    public ChegueResponce(Long id, double priceAverage, LocalDateTime createdAt, int service, int grandTotal) {
        this.id = id;
        this.priceAverage = priceAverage;
        this.createdAt = createdAt;
        this.service = service;
        this.grandTotal = grandTotal;
    }
}
