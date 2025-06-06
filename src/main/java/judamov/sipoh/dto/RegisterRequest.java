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
    Long idTipoDocumento;
    String email;
    String documento;
    String password;
    String firstName;
    String lastName;
    List<Long> idsRoles;
    List<Long> idsAreas;
}
