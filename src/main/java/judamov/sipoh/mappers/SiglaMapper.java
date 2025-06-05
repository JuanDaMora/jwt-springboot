package judamov.sipoh.mappers;

import judamov.sipoh.dto.SiglaDTO;
import judamov.sipoh.entity.Sigla;

public class SiglaMapper {
    public static SiglaDTO siglaToSiglaDTO(Sigla sigla){
        return SiglaDTO.builder()
                .id(sigla.getId())
                .sigla(sigla.getSigla())
                .build();

    }
}
