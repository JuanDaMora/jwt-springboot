package judamov.sipoh.dto;

import judamov.sipoh.entity.Subject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AreaSubjectDTO {
    Long id;
    String description;
    List<SubjectDTO> subjectList;
}