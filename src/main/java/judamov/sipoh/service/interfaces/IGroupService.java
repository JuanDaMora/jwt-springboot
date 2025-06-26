package judamov.sipoh.service.interfaces;

import judamov.sipoh.dto.GroupCreateDTO;
import judamov.sipoh.dto.GroupDTO;
import judamov.sipoh.dto.GroupUpdateDTO;
import judamov.sipoh.entity.Group;

import java.util.List;

public interface IGroupService {
    List<GroupDTO> getAllBySemester(Long idSemester, Long adminId);

    List<GroupDTO> getAllByLevels(List<Long> idLevel, Long adminId);

    List<GroupDTO> getAllBySubject(Long idSubject, Long adminId);
    List<GroupDTO> getAllByDocente(Long idUser, Long adminId);

    GroupDTO createGroup(GroupCreateDTO dto, Long adminId);
    GroupDTO updateGroup(Long groupId, GroupUpdateDTO dto, Long adminId);

}
