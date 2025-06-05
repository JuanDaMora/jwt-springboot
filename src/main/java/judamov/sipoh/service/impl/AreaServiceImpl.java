package judamov.sipoh.service.impl;

import judamov.sipoh.dto.AreaDTO;
import judamov.sipoh.entity.Area;
import judamov.sipoh.exceptions.GenericAppException;
import judamov.sipoh.repository.IAreaRepository;
import judamov.sipoh.service.interfaces.IAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AreaServiceImpl  implements IAreaService {

    private final IAreaRepository areaRepository;
    @Override
    public List<AreaDTO> getAllAreas() {
        return areaRepository.findAll()
                .stream()
                .map(area -> new AreaDTO(area.getId(),area.getDescription()))
                .toList();
    }

    @Override
    public Boolean createArea(AreaDTO areaDTO) {
        areaRepository.findOneByDescription(areaDTO.getDescription().toUpperCase())
                .orElseThrow(()-> new GenericAppException(HttpStatus.NOT_FOUND, "El area ya existe"));

        Area newArea = Area.builder()
                .description(areaDTO.getDescription().toUpperCase())
                .build();
        try {
            areaRepository.save(newArea);
        } catch (Exception e) {
            throw new GenericAppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al guardar el Area");
        }

        return true;
    }
}
