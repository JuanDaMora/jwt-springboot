package judamov.sipoh.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Transactional
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @ManyToOne
    @JoinColumn(name="id_area",nullable = false)
    Area area;
    @ManyToOne
    @JoinColumn(name="id_level_subject",nullable = false)
    LevelSubject levelSubject;
    @Column(name = "codigo")
    String codigo;
    @Column(name = "name")
    String name;
    @Column(name= "max_students")
    Integer max_students;
    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name= "update_date")
    private LocalDateTime updatedAt;
}
