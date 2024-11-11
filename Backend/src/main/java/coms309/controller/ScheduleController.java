package coms309.controller;

import coms309.dto.ScheduleDTO;
import coms309.entity.Schedules;
import coms309.exception.ResourceNotFoundException;
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
    @PostMapping("/create")
    public ResponseEntity<?> createSchedule(@Valid @RequestBody ScheduleDTO scheduleDTO) {
        try {
            if (scheduleDTO.getProjectId() == null) {
                return ResponseEntity.badRequest().body("Project ID must not be null");
            }

            Schedules schedule = scheduleService.createSchedule(scheduleDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(schedule);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error: Unable to create schedule");
        }
    }

    // Get all schedules
    @GetMapping
    public ResponseEntity<?> getSchedules() {
        try {
            List<Schedules> schedules = scheduleService.getAllSchedules();
            return ResponseEntity.ok(schedules);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error: Unable to fetch schedules", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get schedule by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getScheduleById(@PathVariable Long id) {
        try {
            Schedules schedule = scheduleService.getScheduleById(id);
            return ResponseEntity.ok(schedule);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error: Unable to fetch schedule", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get schedules by assigned user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getSchedulesByUser(@PathVariable Long userId) {
        try {
            List<Schedules> schedules = scheduleService.getSchedulesByUser(userId);
            return ResponseEntity.ok(schedules);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error: Unable to fetch schedules", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get schedules by date range
    @GetMapping("/range")
    public ResponseEntity<?> getSchedulesByDateRange(@RequestParam("start") LocalDateTime start, @RequestParam("end") LocalDateTime end) {
        try {
            List<Schedules> schedules = scheduleService.getSchedulesByDateRange(start, end);
            return ResponseEntity.ok(schedules);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error: Unable to fetch schedules", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/assigned/{username}")
    public ResponseEntity<?> getSchedulesAssignedTo(@PathVariable String username) {
        try {
            List<ScheduleDTO> schedules = scheduleService.getSchedulesAssignedTo(username);
            return ResponseEntity.ok(schedules);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error: Unable to fetch schedules for the specified user", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update schedule by ID
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSchedule(@PathVariable Long id, @Valid @RequestBody ScheduleDTO scheduleDTO) {
        try {
            if (scheduleDTO.getProjectId() == null) {
                return new ResponseEntity<>("Project ID must not be null", HttpStatus.BAD_REQUEST);
            }

            Schedules updatedSchedule = scheduleService.updateSchedule(id, scheduleDTO);
            return ResponseEntity.ok(updatedSchedule);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error: Unable to update schedule", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete schedule by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSchedule(@PathVariable Long id) {
        try {
            scheduleService.deleteSchedule(id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal Server Error: Unable to delete schedule", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}