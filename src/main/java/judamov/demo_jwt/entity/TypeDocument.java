package judamov.demo_jwt.User;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "type_document")
public class TypeDocument {
    @Id
    @GeneratedValue
    Integer id;

    @Column(unique = true, nullable = false)
    String description;

}
