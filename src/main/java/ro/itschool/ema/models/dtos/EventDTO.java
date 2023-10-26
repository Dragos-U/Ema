package ro.itschool.ema.models.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;


@Data
@Validated
public class EventDTO implements Serializable {
    private Long id;
    @NotEmpty(message = "Field cannot be empty.")
    private String eventName;

    @NotNull(message = "Field cannot be null.")
    @Future(message = "Invalid date.")
    private LocalDate eventDate;

    @NotNull(message = "Field cannot be null.")
    private LocalTime startTime;

    @NotNull(message = "Field cannot be null.")
    private LocalTime endTime;

    @NotEmpty(message = "Field cannot be empty.")
    private String eventVenue;

    @NotEmpty(message = "Field cannot be empty.")
    private String eventDescription;

    @NotNull(message = "Field cannot be null.")
    @Min(value = 10, message = "Participant number should be greater than 10.")
    @Max(value = 10000, message = "Participant number should be less than 10000.")
    private Integer maxNumOfParticipants;
}
