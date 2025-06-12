package judamov.sipoh.service.interfaces;

import judamov.sipoh.dto.AreaDTO;
import judamov.sipoh.dto.AreaSubjectDTO;

import java.util.List;

public interface IAreaService {
    List<AreaSubjectDTO> getAllAreas();

    Boolean createArea(AreaDTO areaDTO);

    Boolean updateArea(Long id,AreaDTO areaDTO);
}
