package judamov.sipoh.service.interfaces;

import judamov.sipoh.dto.AreaDTO;

import java.util.List;

public interface IAreaService {
    List<AreaDTO> getAllAreas();

    Boolean createArea(AreaDTO areaDTO);

    Boolean updateArea(Long id,AreaDTO areaDTO);
}
