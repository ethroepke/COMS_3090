package coms309.controller;

import coms309.dto.ScheduleDTO;
import coms309.entity.Schedules;
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
    public ResponseEntity<Schedules> createSchedule(@Valid @RequestBody ScheduleDTO scheduleDTO) {
        Schedules schedule = scheduleService.createSchedule(scheduleDTO);
        return new ResponseEntity<>(schedule, HttpStatus.CREATED);
    }

    // Get all schedules
    @GetMapping
    public ResponseEntity<List<Schedules>> getSchedules() {
        List<Schedules> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(schedules);
    }

    // Get schedule by ID
    @GetMapping("/{id}")
    public ResponseEntity<Schedules> getScheduleById(@PathVariable Long id) {
        Schedules schedule = scheduleService.getScheduleById(id);
        return ResponseEntity.ok(schedule);
    }

    // Get schedules by assigned user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Schedules>> getSchedulesByUser(@PathVariable Long userId) {
        List<Schedules> schedules = scheduleService.getSchedulesByUser(userId);
        return ResponseEntity.ok(schedules);
    }

    // Get schedules by date range
    @GetMapping("/range")
    public ResponseEntity<List<Schedules>> getSchedulesByDateRange(
            @RequestParam("start") LocalDateTime start,
            @RequestParam("end") LocalDateTime end) {
        List<Schedules> schedules = scheduleService.getSchedulesByDateRange(start, end);
        return ResponseEntity.ok(schedules);
    }

    // Update schedule by ID
    @PutMapping("/{id}")
    public ResponseEntity<Schedules> updateSchedule(@PathVariable Long id, @Valid @RequestBody ScheduleDTO scheduleDTO) {
        Schedules updatedSchedule = scheduleService.updateSchedule(id, scheduleDTO);
        return ResponseEntity.ok(updatedSchedule);
    }

    // Delete schedule by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}