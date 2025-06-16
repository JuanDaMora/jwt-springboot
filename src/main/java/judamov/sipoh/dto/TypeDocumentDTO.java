package judamov.sipoh.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TypeDocumentDTO {
    private Long id;
    private String description;
    private String sigla;
    private Long idSigla;

}
