package judamov.sipoh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    Integer id;
    String email;
    Integer id_type_document;
    String documento;
    String firstName;
    String lastName;
    Boolean isActive;
    Integer id_Role;
    // auqi pon el role desciption
    LocalDateTime createAt;
    LocalDateTime updatedAt;
}
