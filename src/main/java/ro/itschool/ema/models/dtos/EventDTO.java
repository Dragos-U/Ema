package ro.itschool.ema.models.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;


@Data
@Validated
public class EventDTO implements Serializable {

    private Long id;
    @NotEmpty(message = "eventName cannot be empty.")
    private String eventName;

    @NotNull(message = "eventDate cannot be null.")
    @Future(message = "Invalid date.")
    private LocalDate eventDate;

    @NotNull(message = "startTime cannot be null.")
    private LocalTime startTime;

    @NotNull(message = "endTime cannot be null.")
    private LocalTime endTime;

    @NotEmpty(message = "eventVenue cannot be empty.")
    private String eventVenue;

    @NotEmpty(message = "eventDescription cannot be empty.")
    private String eventDescription;

    @NotEmpty(message = "address cannot be empty.")
    private AddressDTO address;

    @NotNull(message = "maxNumOfParticipants cannot be null.")
    @Min(value = 10, message = "Participant number should be greater than 10.")
    @Max(value = 10000, message = "Participant number should be less than 10000.")
    private Integer maxNumOfParticipants;
}
