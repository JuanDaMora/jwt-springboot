package judamov.sipoh.dto;

import judamov.sipoh.entity.Area;
import judamov.sipoh.enums.DayOfWeekEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GlobalAvabilityDTO {
    Long userIdDocent;
    List<Long> areas;
    Map<DayOfWeekEnum, List<AvailabilityBlockDTO>> disponibilidad;
}
