package judamov.sipoh.service.interfaces;

import judamov.sipoh.dto.SemesterDTO;
import judamov.sipoh.entity.Semester;

import java.util.List;

public interface ISemesterService {
    List<Semester> getAllSemesters();
    Boolean addSemester(SemesterDTO semesterDTO);
    SemesterDTO updateSemester(SemesterDTO semesterDTO);
}
