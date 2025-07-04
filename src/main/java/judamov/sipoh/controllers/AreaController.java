package judamov.sipoh.controllers;

import judamov.sipoh.dto.AreaDTO;
import judamov.sipoh.dto.AreaSubjectDTO;
import judamov.sipoh.service.impl.AreaServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/area")
public class AreaController {

    private final AreaServiceImpl areaService;

    @GetMapping
    public ResponseEntity<List<AreaSubjectDTO>> getAllAreas(){
        return ResponseEntity.ok(areaService.getAllAreas());
    }

    @PostMapping
    public ResponseEntity<Boolean> createArea(@RequestBody AreaDTO areaDTO){
        return ResponseEntity.ok(areaService.createArea(areaDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Boolean> updateArea(@PathVariable Long id,@RequestBody AreaDTO areaDTO){
        return  ResponseEntity.ok(areaService.updateArea(id, areaDTO));
    }

}
