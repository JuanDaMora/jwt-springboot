package judamov.sipoh.service.impl;

import jakarta.transaction.Transactional;
import judamov.sipoh.dto.ScheduleCreateDTO;
import judamov.sipoh.dto.ScheduleDTO;
import judamov.sipoh.dto.ScheduleUpdateDTO;
import judamov.sipoh.entity.Group;
import judamov.sipoh.entity.Schedule;
import judamov.sipoh.entity.User;
import judamov.sipoh.exceptions.GenericAppException;
import judamov.sipoh.repository.IGroupRepository;
import judamov.sipoh.repository.IScheduleRepository;
import judamov.sipoh.repository.IUserRepository;
import judamov.sipoh.service.interfaces.IScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements IScheduleService {
    private final IScheduleRepository scheduleRepository;
    private final UserRolServiceImpl userRolService;
    private final IUserRepository userRepository;
    private final IGroupRepository groupRepository;
    /**
     * Elimina un horario por su ID.
     *
     * @param idSchedule ID del horario a eliminar.
     * @return true si se elimina correctamente.
     */
    @Transactional
    public Boolean deleteSchedule(Long idSchedule,Long adminId) {

        validateAdminAccess(adminId);
        Schedule schedule = scheduleRepository.findById(idSchedule)
                .orElseThrow(() -> new GenericAppException(HttpStatus.BAD_REQUEST, "No existe ningún Schedule con el id: " + idSchedule));
        try {
            scheduleRepository.delete(schedule);
            return true;
        } catch (Exception e) {
            throw new GenericAppException(HttpStatus.INTERNAL_SERVER_ERROR, "Error eliminando el Schedule con id: " + idSchedule);
        }
    }

    /**
     * Elimina múltiples horarios por sus IDs.
     *
     * @param idsSchedules Lista de IDs de horarios a eliminar.
     * @return true si se eliminan todos correctamente.
     */
    @Transactional
    public Boolean deleteSchedule(List<Long> idsSchedules,Long adminId) {
        validateAdminAccess(adminId);
        idsSchedules.forEach(id -> deleteSchedule(id, adminId));
        return true;
    }
    /**
     * Crea un nuevo horario (Schedule) asociado a un grupo.
     *
     * El horario se define mediante el día de la semana y la hora (entera, de 0 a 23).
     * Solo usuarios con permisos de administrador pueden realizar esta operación.
     *
     * @param dto DTO con los datos del horario:
     *  - idGroup: ID del grupo al que se asigna
     *  - hour: hora del día (0-23)
     *  - day: día de la semana
     *
     * @param adminId ID del usuario administrador que realiza la acción.
     *
     * @return DTO con el ID del horario creado, hora y día asignado.
     *
     * @throws GenericAppException si:
     *  - El grupo no existe
     *  - El usuario no tiene permisos
     *  - Ocurre un error al guardar el horario
     */

    @Transactional
    public ScheduleDTO createSchedule(ScheduleCreateDTO dto, Long adminId) {
        validateAdminAccess(adminId);

        Group group = groupRepository.findById(dto.getIdGroup())
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Grupo no encontrado"));

        Schedule schedule = new Schedule();
        schedule.setGroup(group);
        schedule.setDay(dto.getDay());
        schedule.setStartTime(java.time.LocalTime.of(dto.getHour(), 0));

        Schedule saved = scheduleRepository.save(schedule);

        return new ScheduleDTO(saved.getId(), saved.getStartTime().getHour(), saved.getDay());
    }
    /**
     * Actualiza un horario existente (hora y día).
     *
     * @param dto     Objeto con el ID del horario y los nuevos valores.
     * @param adminId ID del usuario administrador que realiza la solicitud.
     * @return DTO actualizado.
     */
    @Transactional
    public ScheduleDTO updateSchedule(ScheduleUpdateDTO dto, Long adminId) {
        validateAdminAccess(adminId);

        Schedule schedule = scheduleRepository.findById(dto.getId())
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Horario no encontrado con id: " + dto.getId()));

        schedule.setStartTime(java.time.LocalTime.of(dto.getHour(), 0));
        schedule.setDay(dto.getDay());

        Schedule updated = scheduleRepository.save(schedule);

        return new ScheduleDTO(updated.getId(), updated.getStartTime().getHour(), updated.getDay());
    }
    /**
     * Actualiza múltiples horarios.
     *
     * @param scheduleList Lista de DTOs con información a actualizar.
     * @param adminId      ID del usuario administrador.
     * @return Lista de horarios actualizados.
     */
    @Transactional
    public List<ScheduleDTO> updateSchedule(List<ScheduleUpdateDTO> scheduleList, Long adminId) {
        return scheduleList.stream()
                .map(dto -> updateSchedule(dto, adminId))
                .toList();
    }


    /**
     * Verifica que el usuario tenga privilegios de administrador.
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
     * Obtiene un usuario por su ID o lanza excepción si no existe.
     *
     * @param userId ID del usuario.
     * @return Entidad User correspondiente.
     */
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }

}
