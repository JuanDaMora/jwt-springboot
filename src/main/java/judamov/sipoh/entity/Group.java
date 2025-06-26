package judamov.sipoh.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import judamov.sipoh.enums.DayOfWeekEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@Getter
@Transactional
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "id_semester", nullable = false)
    private Semester semester;

    @ManyToOne()
    @JoinColumn(name = "id_subject", nullable = false)
    private Subject subject;

    @ManyToOne()
    @JoinColumn(name = "id_user")
    private User user;

    @Column(name="code", nullable = false)
    String code;

    @Column(name="start_time")
    LocalTime startTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    private DayOfWeekEnum dayOfWeek;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name= "updated_at")
    private LocalDateTime updatedAt;
}
