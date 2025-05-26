package judamov.sipoh.controllers;

import judamov.sipoh.entity.Semester;
import judamov.sipoh.service.impl.SemesterServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/semesters")
@RequiredArgsConstructor
public class SemesterController {

    private final SemesterServiceImpl semesterService;
    @GetMapping
    public ResponseEntity<List<Semester>> getSemesters(){
        return ResponseEntity.ok(semesterService.getAllSemesters());
    }

}
