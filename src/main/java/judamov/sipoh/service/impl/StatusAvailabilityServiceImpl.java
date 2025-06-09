package judamov.sipoh.service.impl;

import judamov.sipoh.dto.StatusAvailabilityDTO;
import judamov.sipoh.entity.StatusAvailability;
import judamov.sipoh.repository.IStatusAvailabilityRepository;
import judamov.sipoh.service.interfaces.IStatusAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusAvailabilityServiceImpl implements IStatusAvailabilityService {

    private final IStatusAvailabilityRepository statusAvailabilityRepository;
    @Override
    public List<StatusAvailabilityDTO> getAll(){
        List<StatusAvailability> statusAvailabilityList= statusAvailabilityRepository.findAll();
        return statusAvailabilityList.stream()
                .map(entity -> StatusAvailabilityDTO.builder()
                        .id(entity.getId())
                        .description(entity.getDescription())
                        .build())
                .toList();
    }
}
