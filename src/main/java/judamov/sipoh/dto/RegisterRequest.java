package judamov.sipoh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    List<Integer> idsRoles;
}
