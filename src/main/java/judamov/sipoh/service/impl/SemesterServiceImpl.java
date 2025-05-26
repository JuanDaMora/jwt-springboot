package judamov.sipoh.service.impl;

import judamov.sipoh.entity.Semester;
import judamov.sipoh.repository.ISemesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SemesterServiceImpl {
    private final ISemesterRepository semesterRepository;

    public List<Semester> getAllSemesters(){
        return semesterRepository.findAll();
    }
}
