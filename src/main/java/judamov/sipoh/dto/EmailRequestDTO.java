package judamov.sipoh.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequestDTO {
    private String email;     // Correo del docente
    private String nombre;
    private String documento;
    private String password;
    private Boolean fake;     // true si es simulación
}
