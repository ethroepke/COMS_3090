package coms309.repository;

import coms309.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    // Find schedules by the assigned user's ID
    List<Schedule> findByAssignedUserId(Long userId);

    // Find schedules within a specific start time range
    List<Schedule> findByStartTimeBetween(LocalDateTime start, LocalDateTime end);

    // Find schedules by end time within a specific range
    List<Schedule> findByEndTimeBetween(LocalDateTime start, LocalDateTime end);

    // Find schedules that overlap with a specified time range
    List<Schedule> findByStartTimeLessThanEqualAndEndTimeGreaterThanEqual(LocalDateTime end, LocalDateTime start);

    // Find schedules by user and date range
    List<Schedule> findByAssignedUserIdAndStartTimeBetween(Long userId, LocalDateTime start, LocalDateTime end);
}