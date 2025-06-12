package judamov.sipoh.controllers;

import judamov.sipoh.dto.AvailabilityDTO;
import judamov.sipoh.dto.GlobalAvabilityDTO;
import judamov.sipoh.service.impl.AvailabilityServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityServiceImpl availabilityService;
    @GetMapping("/global")
    public ResponseEntity<List<GlobalAvabilityDTO>> getGlobalAvailability(
            @RequestHeader Long semesterId,
            @RequestHeader Long userId
    ){
        return ResponseEntity.ok(availabilityService.getListGlobalAvailability(semesterId,userId));
    }
    @GetMapping("/global/{docentId}")
    public ResponseEntity<GlobalAvabilityDTO> getGlobalAvailability(
            @RequestHeader Long semesterId,
            @RequestHeader Long userId,
            @PathVariable String docentId
    ){
        return ResponseEntity.ok(availabilityService.getAvailabilityDTO(userId,semesterId,Long.parseLong(docentId)));
    }

    @GetMapping("/docente")
    public ResponseEntity<AvailabilityDTO> getAvailability(
            @RequestHeader Long userId,
            @RequestHeader Long semesterId) {


        AvailabilityDTO dto = availabilityService.getAvailabilityByIdDocent(userId,semesterId);
        return ResponseEntity.ok(dto);
    }
    @PostMapping
    public ResponseEntity<Boolean> createAvailability(
            @RequestHeader Long userId,
            @RequestHeader Long semesterId,
            @RequestBody AvailabilityDTO dto) {
        return ResponseEntity.ok(availabilityService.createAvailability(userId, semesterId, dto));
    }

    @PutMapping("/{availabilityId}")
    public ResponseEntity<Boolean> updateAvailability(@PathVariable Long availabilityId, @RequestHeader Long newStatusId){
        return ResponseEntity.ok(availabilityService.updateAvailabilityStatus(availabilityId,newStatusId));
    }
}
