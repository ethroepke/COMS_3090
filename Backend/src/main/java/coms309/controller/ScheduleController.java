package coms309.controller;

import coms309.dto.ScheduleDTO;
import coms309.entity.Schedule;
import coms309.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    // Create a new schedule
    @PostMapping
    public ResponseEntity<Schedule> createSchedule(@Valid @RequestBody ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleService.createSchedule(scheduleDTO);
        return new ResponseEntity<>(schedule, HttpStatus.CREATED);
    }

    // Get all schedules
    @GetMapping
    public ResponseEntity<List<Schedule>> getSchedules() {
        List<Schedule> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }

    // Get schedule by ID
    @GetMapping("/{id}")
    public ResponseEntity<Schedule> getScheduleById(@PathVariable Long id) {
        Schedule schedule = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(schedule);
    }

    // Get schedules by assigned user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Schedule>> getSchedulesByUser(@PathVariable Long userId) {
        List<Schedule> schedules = scheduleService.getSchedulesByUser(userId);
        return ResponseEntity.ok(schedules);
    }

    // Get schedules by date range
    @GetMapping("/range")
    public ResponseEntity<List<Schedule>> getSchedulesByDateRange(
            @RequestParam("start") LocalDateTime start,
            @RequestParam("end") LocalDateTime end) {
        List<Schedule> schedules = scheduleService.getSchedulesByDateRange(start, end);
        return ResponseEntity.ok(schedules);
    }

    // Update schedule by ID
    @PutMapping("/{id}")
    public ResponseEntity<Schedule> updateSchedule(@PathVariable Long id, @Valid @RequestBody ScheduleDTO scheduleDTO) {
        Schedule updatedSchedule = scheduleService.updateSchedule(id, scheduleDTO);
        return ResponseEntity.ok(updatedSchedule);
    }

    // Delete schedule by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}