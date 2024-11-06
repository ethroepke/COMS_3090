package coms309.service;

import coms309.dto.ScheduleDTO;
import coms309.entity.Schedule;
import coms309.entity.User;
import coms309.exception.ResourceNotFoundException;
import coms309.repository.ScheduleRepository;
import coms309.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a new schedule
    public Schedule createSchedule(ScheduleDTO scheduleDTO) {
        User user = userRepository.findById(scheduleDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + scheduleDTO.getUserId()));

        Schedule schedule = new Schedule();
        schedule.setEventType(scheduleDTO.getEventType());
        schedule.setStartTime(scheduleDTO.getStartTime());
        schedule.setEndTime(scheduleDTO.getEndTime());
        schedule.setAssignedUser(user);

        return scheduleRepository.save(schedule);
    }

    // Retrieve all schedules
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    // Retrieve a schedule by ID
    public Schedule getScheduleById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + id));
    }

    // Retrieve schedules by user ID
    public List<Schedule> getSchedulesByUser(Long userId) {
        return scheduleRepository.findByAssignedUserId(userId);
    }

    // Retrieve schedules within a specific date range
    public List<Schedule> getSchedulesByDateRange(LocalDateTime start, LocalDateTime end) {
        return scheduleRepository.findByStartTimeBetween(start, end);
    }

    // Update an existing schedule
    @Transactional
    public Schedule updateSchedule(Long id, ScheduleDTO scheduleDTO) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + id));

        User user = userRepository.findById(scheduleDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + scheduleDTO.getUserId()));

        schedule.setEventType(scheduleDTO.getEventType());
        schedule.setStartTime(scheduleDTO.getStartTime());
        schedule.setEndTime(scheduleDTO.getEndTime());
        schedule.setAssignedUser(user);

        return scheduleRepository.save(schedule);
    }

    // Delete a schedule by ID
    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule not found with id: " + id));

        scheduleRepository.delete(schedule);
    }
}