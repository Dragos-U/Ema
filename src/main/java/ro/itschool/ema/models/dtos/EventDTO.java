package ro.itschool.ema.models.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Data
@Validated
public class EventDTO implements Serializable {
    private Long id;

    @NotEmpty(message = "Event field cannot be empty.")
    private String eventName;

    @NotNull(message = "Event date field cannot be null.")
    @Future(message = "Invalid date.")
    private LocalDate eventDate;

    @NotNull(message = "Start time field cannot be null.")
    private LocalTime startTime;

    @NotNull(message = "End time field cannot be null.")
    private LocalTime endTime;

    @NotEmpty(message = "Event venue field cannot be empty.")
    private String eventVenue;

    @NotEmpty(message = "Event description field cannot be empty.")
    private String eventDescription;

    @NotNull(message = "Address field cannot be null")
    private AddressDTO address;

    @NotNull(message = "Maximum number of participants field cannot be null.")
    @Min(value = 10, message = "Participant number should be greater than 10.")
    @Max(value = 10000, message = "Participant number should be less than 10000.")
    private Integer maxNumOfParticipants;

    @NotNull(message = "Organizer field cannot be null.")
    private Long organizerId;

    private Set<OrganizerDTO> organizers;
}
