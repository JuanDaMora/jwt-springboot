package judamov.sipoh.service.impl;

import jakarta.transaction.Transactional;
import judamov.sipoh.dto.SemesterDTO;
import judamov.sipoh.entity.Semester;
import judamov.sipoh.entity.User;
import judamov.sipoh.exceptions.GenericAppException;
import judamov.sipoh.repository.ISemesterRepository;
import judamov.sipoh.service.interfaces.ISemesterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SemesterServiceImpl implements ISemesterService {
    private final ISemesterRepository semesterRepository;
    @Override
    public List<Semester> getAllSemesters(){
        return semesterRepository.findAll();
    }
    @Override
    @Transactional
    public Boolean addSemester(SemesterDTO semesterDTO){
        semesterRepository.findOneByDescription(semesterDTO.getDescription())
                .ifPresent(semester -> {
                    throw new GenericAppException(HttpStatus.CONFLICT, "Este semestre ya existe");
                });
        Semester newSemester = new Semester(semesterDTO);
        try{
            semesterRepository.save(newSemester);
        }catch(Exception e){
            throw new GenericAppException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return true;
    }
    @Override
    @Transactional
    public SemesterDTO updateSemester(SemesterDTO semesterDTO){
        Semester semester= semesterRepository.findOneById(semesterDTO.getId())
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Semestre no encontrado"));

        semester.updateFromDto(semesterDTO);
        Semester saved = semesterRepository.save(semester);

        return new SemesterDTO(saved);

    }
}
