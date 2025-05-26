package judamov.sipoh.service.impl;

import judamov.sipoh.dto.SemesterDTO;
import judamov.sipoh.entity.Semester;
import judamov.sipoh.entity.User;
import judamov.sipoh.exceptions.GenericAppException;
import judamov.sipoh.repository.ISemesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SemesterServiceImpl {
    private final ISemesterRepository semesterRepository;

    public List<Semester> getAllSemesters(){
        return semesterRepository.findAll();
    }

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

    public SemesterDTO updateSemester(SemesterDTO semesterDTO){
        Semester semester= semesterRepository.findOneById(semesterDTO.getId())
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Semestre no encontrado"));

        semester.updateFromDto(semesterDTO);
        Semester saved = semesterRepository.save(semester);

        return new SemesterDTO(saved);

    }
}
