package judamov.sipoh.controllers;

import judamov.sipoh.dto.AvailabilityDTO;
import judamov.sipoh.service.impl.AvailabilityServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityServiceImpl availabilityService;

    @GetMapping
    public ResponseEntity<AvailabilityDTO> getAvailability(
            @RequestHeader Long userId,
            @RequestHeader Long semesterId) {


        AvailabilityDTO dto = availabilityService.getAvailability(userId,semesterId);
        return ResponseEntity.ok(dto);
    }
    @PostMapping
    public ResponseEntity<Boolean> createAvailability(
            @RequestHeader Long userId,
            @RequestHeader Long semesterId,
            @RequestBody AvailabilityDTO dto) {
        return ResponseEntity.ok(availabilityService.createAvailability(userId, semesterId, dto));
    }
}
