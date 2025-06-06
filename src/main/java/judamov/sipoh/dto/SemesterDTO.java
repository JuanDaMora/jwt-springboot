package judamov.sipoh.dto;

import jakarta.persistence.Column;
import judamov.sipoh.entity.Semester;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class SemesterDTO {
    Long id;
    String description;
    LocalDate startDate;
    LocalDate endDate;

    public SemesterDTO(Semester semester) {
        this.id = semester.getId();
        this.description = semester.getDescription();
        this.startDate = semester.getStartDate();
        this.endDate = semester.getEndDate();
    }
}
