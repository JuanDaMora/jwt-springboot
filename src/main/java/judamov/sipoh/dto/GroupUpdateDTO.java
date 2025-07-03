package judamov.sipoh.dto;

import judamov.sipoh.entity.Schedule;
import judamov.sipoh.enums.DayOfWeekEnum;
import lombok.Data;

import java.util.List;

@Data
public class GroupUpdateDTO {
    private String code;
    private Long idSemestre;
    private Long idSubject;
    private Long idUser;
}
