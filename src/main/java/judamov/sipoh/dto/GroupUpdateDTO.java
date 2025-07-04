package judamov.sipoh.dto;

import lombok.Data;

@Data
public class GroupUpdateDTO {
    private String code;
    private Long idSemester;
    private Long idSubject;
    private Long idDocente;
}
