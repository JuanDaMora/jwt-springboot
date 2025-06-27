package judamov.sipoh.dto;
import judamov.sipoh.enums.DayOfWeekEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleCreateDTO {
    private Long idGroup;
    private Integer hour;
    private DayOfWeekEnum day;
}
