package judamov.sipoh.service.impl;

import jakarta.transaction.Transactional;
import judamov.sipoh.dto.AvailabilityBlockDTO;
import judamov.sipoh.dto.AvailabilityDTO;
import judamov.sipoh.entity.Availability;
import judamov.sipoh.entity.Semester;
import judamov.sipoh.entity.StatusAvailability;
import judamov.sipoh.entity.User;
import judamov.sipoh.enums.DayOfWeekEnum;
import judamov.sipoh.exceptions.GenericAppException;
import judamov.sipoh.repository.IAvailabilityRepository;
import judamov.sipoh.repository.ISemesterRepository;
import judamov.sipoh.repository.IStatusAvailabilityRepository;
import judamov.sipoh.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl {

    private final IAvailabilityRepository availabilityRepository;
    private final IUserRepository userRepository;
    private final ISemesterRepository semesterRepository;
    private final IStatusAvailabilityRepository statusAvailabilityRepository;

    public AvailabilityDTO getAvailability(Integer userId, Integer semesterId) {
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
    public Boolean createAvailability(Integer userId, Integer semesterId, AvailabilityDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));
        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Semestre no encontrado"));

        availabilityRepository.deleteByUserAndSemester(user, semester);

        StatusAvailability defaultStatus = statusAvailabilityRepository.findById(1)
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Estado por defecto no encontrado"));

        for (Map.Entry<DayOfWeekEnum, List<AvailabilityBlockDTO>> entry : dto.getDisponibilidad().entrySet()) {
            DayOfWeekEnum day = entry.getKey();

            for (AvailabilityBlockDTO block : entry.getValue()) {
                LocalTime time = LocalTime.of(block.getHour(), 0);
                boolean exists = availabilityRepository.existsByUserAndSemesterAndDayOfWeekAndStartTime(user, semester, day, time);

                if (exists) {
                    throw new GenericAppException(HttpStatus.CONFLICT, "Ya existe disponibilidad en " + day + " a las " + time);
                }

                Availability availability = new Availability();
                availability.setUser(user);
                availability.setSemester(semester);
                availability.setDayOfWeek(day);
                availability.setStartTime(time);
                StatusAvailability status;
                if (block.getStatusId() != null) {
                    status = statusAvailabilityRepository.findById(block.getStatusId())
                            .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Estado no encontrado: " + block.getStatusId()));
                } else {
                    status = defaultStatus;
                }
                availability.setStatusAvailability(status);
                availabilityRepository.save(availability);
            }
        }

        return true;
    }

}
