package judamov.sipoh.dto;

import judamov.sipoh.enums.DayOfWeekEnum;
import lombok.Data;

@Data
public class GroupUpdateDTO {
    private String code;
    private Long idSemestre;
    private Long idSubject;
    private Long idUser;
    private DayOfWeekEnum dayOfWeek;
    private Integer hour; // solo hora entera (ej. 13 para 1 PM)
}
