package judamov.sipoh.service.impl;

import jakarta.transaction.Transactional;
import judamov.sipoh.dto.AvailabilityBlockDTO;
import judamov.sipoh.dto.AvailabilityDTO;
import judamov.sipoh.entity.*;
import judamov.sipoh.enums.DayOfWeekEnum;
import judamov.sipoh.exceptions.GenericAppException;
import judamov.sipoh.repository.*;
import judamov.sipoh.service.interfaces.IAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements IAvailabilityService {

    private final IAvailabilityRepository availabilityRepository;
    private final IUserRoleRepository userRoleRepository;
    private final IUserRepository userRepository;
    private final ISemesterRepository semesterRepository;
    private final IStatusAvailabilityRepository statusAvailabilityRepository;
    @Override
    public AvailabilityDTO getAvailability(Long userId, Long semesterId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Semestre no encontrado"));

        List<Availability> availabilityList = availabilityRepository.findByUserAndSemester(user, semester)
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "No se encontró disponibilidad"));

        Map<DayOfWeekEnum, List<AvailabilityBlockDTO>> availabilityMap = availabilityList.stream()
                .collect(Collectors.groupingBy(
                        Availability::getDayOfWeek,
                        Collectors.mapping(a -> AvailabilityBlockDTO.builder()
                                .hour(a.getStartTime().getHour())
                                .statusId(a.getStatusAvailability().getId())
                                .statusDescription(a.getStatusAvailability().getDescription())
                                .build(), Collectors.toList())
                ));

        return AvailabilityDTO.builder().disponibilidad(availabilityMap).build();
    }
    @Transactional
    public Boolean createAvailability(Long userId, Long semesterId, AvailabilityDTO dto) {
        User user = getUserById(userId);
        Semester semester = getSemesterById(semesterId);

        List<Availability> currentAvailability = getCurrentAvailability(user, semester);
        Map<String, AvailabilityBlockDTO> incomingMap = buildIncomingAvailabilityMap(dto);

        StatusAvailability defaultStatus = getDefaultStatus();

        deleteObsoleteAvailability(currentAvailability, incomingMap, userId);

        saveNewAvailabilityBlocks(dto, user, semester, incomingMap, defaultStatus);

        return true;
    }
    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
    }
    @Override
    public Semester getSemesterById(Long semesterId) {
        return semesterRepository.findById(semesterId)
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Semestre no encontrado"));
    }
    @Override
    public StatusAvailability getDefaultStatus() {
        return statusAvailabilityRepository.findById(1L)
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Estado por defecto no encontrado"));
    }
    @Override
    public List<Availability> getCurrentAvailability(User user, Semester semester) {
        return availabilityRepository.findByUserAndSemester(user, semester)
                .orElse(new ArrayList<>());
    }
    @Override
    public Map<String, AvailabilityBlockDTO> buildIncomingAvailabilityMap(AvailabilityDTO dto) {
        return dto.getDisponibilidad().entrySet().stream()
                .flatMap(e -> e.getValue().stream()
                        .map(block -> Map.entry(e.getKey() + "-" + block.getHour(), block)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    @Override
    @Transactional
    public void deleteObsoleteAvailability(List<Availability> currentAvailability,
                                            Map<String, AvailabilityBlockDTO> incomingMap, Long userRequestId) {
        User userRequest=this.getUserById(userRequestId);
        List<UserRol> userRolesRequest = userRoleRepository.findAllByUser(userRequest)
                .orElseThrow(()-> new GenericAppException(HttpStatus.NOT_FOUND, "Roles del usuario no encontrados"));
        boolean isAdmin=false;
        /**
         * Busca entre los roles para veroficar si
         * puede eliminar las disponibilidades rechazadas
         */
        for(UserRol userRole : userRolesRequest) {
            if(!userRole.getRole().getName().contains("PROFESOR")) {
                isAdmin=true;
                break;
            }
        }
        for (Availability existing : currentAvailability) {
            String key = existing.getDayOfWeek() + "-" + existing.getStartTime().getHour();
            boolean existsInNew = incomingMap.containsKey(key);
            if(isAdmin){
                availabilityRepository.delete(existing);
            } else if  (!existsInNew &&
                    !existing.getStatusAvailability().getDescription().equalsIgnoreCase("APROBADO")
                    && !existing.getStatusAvailability().getDescription().equalsIgnoreCase("RECHAZADO")) {
                availabilityRepository.delete(existing);
            }
        }
    }
    @Override
    @Transactional
    public void saveNewAvailabilityBlocks(AvailabilityDTO dto, User user, Semester semester,
                                           Map<String, AvailabilityBlockDTO> incomingMap,
                                           StatusAvailability defaultStatus) {

        for (Map.Entry<DayOfWeekEnum, List<AvailabilityBlockDTO>> entry : dto.getDisponibilidad().entrySet()) {
            DayOfWeekEnum day = entry.getKey();

            for (AvailabilityBlockDTO block : entry.getValue()) {
                LocalTime time = LocalTime.of(block.getHour(), 0);
                String key = day + "-" + block.getHour();

                if (availabilityRepository.existsByUserAndSemesterAndDayOfWeekAndStartTime(user, semester, day, time)) {
                    continue;
                }

                Availability availability = new Availability();
                availability.setUser(user);
                availability.setSemester(semester);
                availability.setDayOfWeek(day);
                availability.setStartTime(time);

                StatusAvailability status = (block.getStatusId() != null)
                        ? statusAvailabilityRepository.findById(block.getStatusId())
                        .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Estado no encontrado: " + block.getStatusId()))
                        : defaultStatus;

                availability.setStatusAvailability(status);
                availabilityRepository.save(availability);
            }
        }
    }
}
