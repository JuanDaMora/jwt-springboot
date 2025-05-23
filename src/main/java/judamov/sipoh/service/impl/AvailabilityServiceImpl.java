package judamov.sipoh.service.impl;

import judamov.sipoh.dto.AvailabilityDTO;
import judamov.sipoh.entity.Availability;
import judamov.sipoh.entity.Semester;
import judamov.sipoh.entity.User;
import judamov.sipoh.enums.DayOfWeekEnum;
import judamov.sipoh.exceptions.GenericAppException;
import judamov.sipoh.repository.IAvailabilityRepository;
import judamov.sipoh.repository.ISemesterRepository;
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

    public AvailabilityDTO getAvailability(Integer userId, Integer semesterId) {


        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND,"Semestre no encontrado"));

        List<Availability> availabilityList = availabilityRepository.findByUserAndSemester(user, semester)
                .orElseThrow(()-> new GenericAppException(HttpStatus.NOT_FOUND, "No se encontro la disponibilidad")) ;

        Map<DayOfWeekEnum, List<Integer>> availabilityMap = availabilityList.stream()
                .collect(Collectors.groupingBy(
                        Availability::getDayOfWeek,
                        Collectors.mapping(a -> a.getStartTime().getHour(), Collectors.toList())
                ));

        return AvailabilityDTO.builder()
                .disponibilidad(availabilityMap)
                .build();
    }

    public Boolean updateAvailability(Integer userId, Integer  semesterId, AvailabilityDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND,"Semestre no encontrado"));

        availabilityRepository.deleteByUserAndSemester(user, semester);

        for (Map.Entry<DayOfWeekEnum, List<Integer>> entry : dto.getDisponibilidad().entrySet()) {
            DayOfWeekEnum day = entry.getKey();
            for (Integer hour : entry.getValue()) {
                Availability availability = new Availability();
                availability.setUser(user);
                availability.setSemester(semester);
                availability.setDayOfWeek(day);
                availability.setStartTime(LocalTime.of(hour, 0));
                availabilityRepository.save(availability);
            }
        }
        return true;
    }
    public Boolean createAvailability(Integer userId, Integer semesterId, AvailabilityDTO dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Semester semester = semesterRepository.findById(semesterId)
                .orElseThrow(() -> new GenericAppException(HttpStatus.NOT_FOUND,"Semestre no encontrado"));

        for (Map.Entry<DayOfWeekEnum, List<Integer>> entry : dto.getDisponibilidad().entrySet()) {
            DayOfWeekEnum day = entry.getKey();

            for (Integer hour : entry.getValue()) {
                LocalTime time = LocalTime.of(hour, 0);

                boolean exists = availabilityRepository.existsByUserAndSemesterAndDayOfWeekAndStartTime(
                        user, semester, day, time
                );

                if (exists) {
                    throw new GenericAppException(HttpStatus.CONFLICT, "Ya existe una disponibilidad en " + day + " a las " + time);
                }

                Availability availability = new Availability();
                availability.setUser(user);
                availability.setSemester(semester);
                availability.setDayOfWeek(day);
                availability.setStartTime(time);
                availabilityRepository.save(availability);
            }
        }
        return true;
    }

}
