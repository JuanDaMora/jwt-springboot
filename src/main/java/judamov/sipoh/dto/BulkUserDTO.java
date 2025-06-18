package judamov.sipoh.dto;

import lombok.*;

import java.util.List;@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BulkUserDTO {
    private String firstName;
    private String lastName;
    private String correoPersonal;
    private String correoInstitucional;
    private String celular;
    private String documento;
    private Long idTipoDocumento;
    private List<Long> idsRoles;
    private List<Long> idAreas;
}
