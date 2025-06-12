package judamov.sipoh.repository;

import judamov.sipoh.entity.Availability;
import judamov.sipoh.entity.Semester;
import judamov.sipoh.entity.User;
import judamov.sipoh.enums.DayOfWeekEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface IAvailabilityRepository extends JpaRepository<Availability, Long> {
    Optional<List<Availability>> findByUserAndSemester(User user, Semester semester);

    Optional<List<Availability>> findBySemester(Semester semester);

    void deleteByUserAndSemester(User user, Semester semester);

    boolean existsByUserAndSemesterAndDayOfWeekAndStartTime(
            User user, Semester semester, DayOfWeekEnum dayOfWeek, LocalTime startTime
    );
}
