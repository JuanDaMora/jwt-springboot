package judamov.sipoh.service.impl;

import jakarta.transaction.Transactional;
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

    @Transactional
    @Override
    public Boolean createArea(AreaDTO areaDTO) {
        areaRepository.findOneByDescription(areaDTO.getDescription().toUpperCase())
                .ifPresent(u -> {
                    throw new GenericAppException(HttpStatus.BAD_REQUEST, "El area ya existe");
                });

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
    @Transactional
    @Override
    public Boolean updateArea(Long idArea, AreaDTO areaDTO){
        Area area=areaRepository.findOneById(idArea)
                .orElseThrow(
                        ()-> new GenericAppException(HttpStatus.NOT_FOUND,
                                "No se encontro el area con id: "+areaDTO.getId())
                );
        area.setDescription(areaDTO.getDescription());
        try{
            areaRepository.save(area);
        }catch (Exception e){
            throw new GenericAppException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error al actualizar el area con id: "+ areaDTO.getId());
        }
        return true;
    }

}
