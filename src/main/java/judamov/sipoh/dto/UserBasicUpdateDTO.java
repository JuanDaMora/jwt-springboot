package judamov.sipoh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBasicUpdateDTO {
    private Long id;
    private String email;
    private Long idTipoDocumento;
    private String documento;
    private String firstName;
    private String lastName;
}