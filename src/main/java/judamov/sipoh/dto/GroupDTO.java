package judamov.sipoh.dto;

import judamov.sipoh.enums.DayOfWeekEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {
    private Long id;
    private Long idSemestre;
    private Long idSubject;
    private Long idUser;
    private Long idLevel;
    private String levelName;
    private String name;
    private DayOfWeekEnum dayOfWeek;
    private Integer hour;
}
