package judamov.sipoh.dto;

import judamov.sipoh.enums.DayOfWeekEnum;
import lombok.Data;

import java.util.List;

@Data
public class GroupCreateDTO {
    private String code;
    private Long idSemestre;
    private Long idSubject;
    private Long idDocente;
    private List<ScheduleDTO> scheduleList;
}
