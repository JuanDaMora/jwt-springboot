package judamov.sipoh.service.impl;

import judamov.sipoh.dto.GroupCreateDTO;
import judamov.sipoh.dto.GroupDTO;
import judamov.sipoh.dto.GroupUpdateDTO;
import judamov.sipoh.entity.Group;
import judamov.sipoh.entity.Semester;
import judamov.sipoh.entity.Subject;
import judamov.sipoh.entity.User;
import judamov.sipoh.exceptions.GenericAppException;
import judamov.sipoh.mappers.GroupMapper;
import judamov.sipoh.repository.IGroupRepository;
import judamov.sipoh.repository.ISemesterRepository;
import judamov.sipoh.repository.ISubjectRepository;
import judamov.sipoh.repository.IUserRepository;
import judamov.sipoh.service.interfaces.IGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

/**
 * Servicio encargado de la gestión de grupos (Group).
 * Permite crear, consultar y validar grupos según semestres, niveles, materias o docentes.
 */
@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements IGroupService {

    private final IGroupRepository groupRepository;
    private final IUserRepository userRepository;
    private final UserRolServiceImpl userRolService;
    private final ISubjectRepository subjectRepository;
    private final ISemesterRepository semesterRepository;

    /**
     * Obtiene todos los grupos asociados a una lista de semestres.
     *
     * @param idSemester Lista de IDs de semestres.
     * @param adminId     ID del usuario administrador que realiza la solicitud.
     * @return Lista de grupos convertidos en DTO.
     */
    @Override
    public List<GroupDTO> getAllBySemester(Long idSemester, Long adminId) {
        validateAdminAccess(adminId);
        List<Group> groups = groupRepository.findAll().stream()
                .filter(group -> group.getSemester() != null && group.getSemester().getId().equals(idSemester))
                .toList();
        return GroupMapper.toDTOList(groups);
    }


    /**
     * Obtiene todos los grupos cuyo nivel pertenezca a los niveles especificados.
     *
     * @param idLevels Lista de IDs de niveles.
     * @param adminId  ID del usuario administrador que realiza la solicitud.
     * @return Lista de grupos convertidos en DTO.
     */
    @Override
    public List<GroupDTO> getAllByLevels(List<Long> idLevels, Long adminId) {
        validateAdminAccess(adminId);
        List<Group> groups = groupRepository.findAll().stream()
                .filter(group -> group.getSubject() != null &&
                        group.getSubject().getLevelSubject() != null &&
                        idLevels.contains(group.getSubject().getLevelSubject().getId()))
                .toList();
        return GroupMapper.toDTOList(groups);
    }

    /**
     * Obtiene todos los grupos asociados a una materia específica.
     *
     * @param idSubject ID de la materia.
     * @param adminId   ID del usuario administrador que realiza la solicitud.
     * @return Lista de grupos convertidos en DTO.
     */
    @Override
    public List<GroupDTO> getAllBySubject(Long idSubject, Long adminId) {
        validateAdminAccess(adminId);
        Subject subject = subjectRepository.findById(idSubject)
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Materia no encontrada"));
        List<Group> groups = groupRepository.findBySubject(subject)
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "No hay grupos para esta materia"));
        return GroupMapper.toDTOList(groups);
    }

    /**
     * Obtiene todos los grupos asignados a un docente específico.
     *
     * @param idUser  ID del docente.
     * @param adminId ID del usuario administrador que realiza la solicitud.
     * @return Lista de grupos convertidos en DTO.
     */
    @Override
    public List<GroupDTO> getAllByDocente(Long idUser, Long adminId) {
        validateAdminAccess(adminId);
        User docente = getUserById(idUser);
        List<Group> groups = groupRepository.findByUser(docente)
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "El docente no tiene grupos asignados"));
        return GroupMapper.toDTOList(groups);
    }

    /**
     * Crea un nuevo grupo con o sin docente asignado.
     *
     * @param dto     Datos del grupo a crear.
     * @param adminId ID del usuario administrador que realiza la solicitud.
     * @return DTO del grupo creado.
     */
    @Override
    public GroupDTO createGroup(GroupCreateDTO dto, Long adminId) {
        validateAdminAccess(adminId);

        Subject subject = subjectRepository.findById(dto.getIdSubject())
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Materia no encontrada"));

        Semester semester = semesterRepository.findById(dto.getIdSemestre())
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Semestre no encontrado"));

        User user = (dto.getIdUser() != null) ? getUserById(dto.getIdUser()) : null;

        Group group = new Group();
        group.setCode(dto.getCode());
        group.setSemester(semester);
        group.setSubject(subject);
        group.setUser(user);
        group.setDayOfWeek(dto.getDayOfWeek());
        group.setStartTime(dto.getHour() != null ? LocalTime.of(dto.getHour(), 0) : null);

        Group saved = groupRepository.save(group);
        return GroupMapper.toDTO(saved);
    }
    @Override
    public GroupDTO updateGroup(Long groupId, GroupUpdateDTO dto, Long adminId) {
        validateAdminAccess(adminId);

        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Grupo no encontrado"));

        Subject subject = subjectRepository.findById(dto.getIdSubject())
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Materia no encontrada"));

        Semester semester = semesterRepository.findById(dto.getIdSemestre())
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Semestre no encontrado"));

        User user = (dto.getIdUser() != null) ? getUserById(dto.getIdUser()) : null;

        group.setCode(dto.getCode());
        group.setSemester(semester);
        group.setSubject(subject);
        group.setUser(user);
        group.setDayOfWeek(dto.getDayOfWeek());
        group.setStartTime(dto.getHour() != null ? LocalTime.of(dto.getHour(), 0) : null);

        Group updated = groupRepository.save(group);
        return GroupMapper.toDTO(updated);
    }


    /**
     * Verifica que un usuario tenga privilegios de administrador.
     *
     * @param userId ID del usuario a validar.
     */
    private void validateAdminAccess(Long userId) {
        User user = getUserById(userId);
        if (!userRolService.hasAdminPrivileges(user)) {
            throw new GenericAppException(HttpStatus.UNAUTHORIZED, "No autorizado para esta solicitud");
        }
    }

    /**
     * Obtiene un usuario por su ID o lanza una excepción si no existe.
     *
     * @param userId ID del usuario.
     * @return Entidad User encontrada.
     */
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }
}
