package judamov.sipoh.mappers;

import judamov.sipoh.dto.GroupDTO;
import judamov.sipoh.entity.Group;

import java.util.List;
import java.util.stream.Collectors;

public class GroupMapper {

    public static GroupDTO toDTO(Group group) {
        if (group == null) return null;

        return GroupDTO.builder()
                .id(group.getId())
                .name(group.getName())
                .idSemestre(group.getSemester() != null ? group.getSemester().getId() : null)
                .idSubject(group.getSubject() != null ? group.getSubject().getId() : null)
                .idUser(group.getUser() != null ? group.getUser().getId() : null)
                .idLevel(group.getSubject() != null && group.getSubject().getLevelSubject() != null
                        ? group.getSubject().getLevelSubject().getId()
                        : null)
                .levelName(group.getSubject() != null && group.getSubject().getLevelSubject() != null
                        ? group.getSubject().getLevelSubject().getDescription()
                        : null)
                .dayOfWeek(group.getDayOfWeek())
                .hour(group.getStartTime() != null ? group.getStartTime().getHour() : null)
                .build();
    }

    public static List<GroupDTO> toDTOList(List<Group> groups) {
        return groups.stream()
                .map(GroupMapper::toDTO)
                .collect(Collectors.toList());
    }
}
