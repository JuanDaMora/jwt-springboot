package judamov.sipoh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailabilityBlockDTO {
    private Long id;
    private Integer hour;
    private Long statusId;
    private String statusDescription;
}
