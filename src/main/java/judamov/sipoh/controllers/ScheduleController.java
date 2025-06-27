package judamov.sipoh.controllers;

import judamov.sipoh.dto.ScheduleCreateDTO;
import judamov.sipoh.dto.ScheduleDTO;
import judamov.sipoh.dto.ScheduleUpdateDTO;
import judamov.sipoh.service.impl.ScheduleServiceImpl;
import judamov.sipoh.service.interfaces.IScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleServiceImpl scheduleService;

    /**
     * Crea un nuevo horario.
     */
    @PostMapping
    public ResponseEntity<ScheduleDTO> createSchedule(
            @RequestBody ScheduleCreateDTO dto,
            @RequestHeader("userId") Long adminId) {
        return ResponseEntity.ok(scheduleService.createSchedule(dto, adminId));
    }

    /**
     * Actualiza un horario existente.
     */
    @PutMapping
    public ResponseEntity<ScheduleDTO> updateSchedule(
            @RequestBody ScheduleUpdateDTO dto,
            @RequestHeader("userId") Long adminId) {
        return ResponseEntity.ok(scheduleService.updateSchedule(dto, adminId));
    }

    /**
     * Actualiza múltiples horarios.
     */
    @PutMapping("/batch")
    public ResponseEntity<List<ScheduleDTO>> updateSchedules(
            @RequestBody List<ScheduleUpdateDTO> dtoList,
            @RequestHeader("userId") Long adminId) {
        return ResponseEntity.ok(scheduleService.updateSchedule(dtoList, adminId));
    }

    /**
     * Elimina un horario por ID.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteSchedule(
            @PathVariable("id") Long idSchedule,
            @RequestHeader("userId") Long adminId) {
        return ResponseEntity.ok(scheduleService.deleteSchedule(idSchedule,adminId));
    }

    /**
     * Elimina múltiples horarios por ID.
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteSchedules(
            @RequestBody List<Long> idsSchedules,
            @RequestHeader("userId") Long adminId) {
        return ResponseEntity.ok(scheduleService.deleteSchedule(idsSchedules,adminId));
    }
}
