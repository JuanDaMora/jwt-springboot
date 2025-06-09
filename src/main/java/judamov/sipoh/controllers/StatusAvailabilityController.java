package judamov.sipoh.controllers;

import judamov.sipoh.dto.StatusAvailabilityDTO;
import judamov.sipoh.service.impl.StatusAvailabilityServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/status-availability")
public class StatusAvailabilityController {

    private final StatusAvailabilityServiceImpl statusAvailabilityService;

    @GetMapping
    public ResponseEntity<List<StatusAvailabilityDTO>> getAll(){
        return ResponseEntity.ok(statusAvailabilityService.getAll());
    }

}
