package judamov.sipoh.service.interfaces;

import java.util.List;

public interface IScheduleService {
    Boolean deleteSchedule(Long idSchedule,Long adminId);
    Boolean deleteSchedule(List<Long> idSchedules,Long adminId);
}
