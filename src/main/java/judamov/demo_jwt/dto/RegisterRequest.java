package judamov.demo_jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    Integer idTipoDocumento;
    String email;
    String documento;
    String password;
    String firstName;
    String lastName;
    Boolean isProfesor;
    Integer idRol;
}
