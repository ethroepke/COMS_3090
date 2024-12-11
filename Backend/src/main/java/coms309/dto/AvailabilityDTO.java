package coms309.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Data Transfer Object for Availability entity.
 */
@Getter
@Setter
public class AvailabilityDTO {
    private Long availabilityId;
    private DayOfWeek dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
