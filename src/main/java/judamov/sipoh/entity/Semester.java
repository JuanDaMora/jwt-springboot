package judamov.sipoh.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import judamov.sipoh.dto.SemesterDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "semester")
@Transactional
@NoArgsConstructor
@AllArgsConstructor
public class Semester {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique = true, nullable = false)
    String description;
    @Column(unique = true, nullable = false)
    LocalDate startDate;
    @Column(nullable = false)
    LocalDate endDate;

    @Column(name = "creation_date", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name= "update_date")
    private LocalDateTime updatedAt;

    public Semester(SemesterDTO semesterDTO) {
        this.description = semesterDTO.getDescription();
        this.startDate = semesterDTO.getStartDate();
        this.endDate = semesterDTO.getEndDate();
    }

    public void updateFromDto(SemesterDTO dto) {
        this.description = dto.getDescription();
        this.startDate = dto.getStartDate();
        this.endDate = dto.getEndDate();
    }
}
