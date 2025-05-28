package judamov.sipoh.entity;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Transactional
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sigla")
public class Sigla {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "sigla")
    private String sigla;
    @Column(name = "creation_date", updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(name= "update_date")
    private LocalDateTime updatedAt;

}
