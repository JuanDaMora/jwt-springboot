package judamov.sipoh.dto;

import judamov.sipoh.enums.DayOfWeekEnum;
import lombok.Data;

@Data
public class GroupCreateDTO {
    private String code;
    private Long idSemestre;
    private Long idSubject;
    private Long idUser; // opcional
    private DayOfWeekEnum dayOfWeek;
    private Integer hour; // solo hora (ej. 14 para 2 PM)
}
