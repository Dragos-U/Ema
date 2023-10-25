package ro.itschool.ema.models.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class EventDTO implements Serializable {
    private Long id;
    @NotNull(message = "Field cannot be null.")
    private String eventName;
    @NotNull(message = "Field cannot be null.")
    @Past(message = "Invalid date.")
    private Date eventDate;
    @NotNull(message = "Field cannot be null.")
    private String eventVenue;
    @NotNull(message = "Field cannot be null.")
    private String eventDescription;
    @NotNull(message = "Field cannot be null.")
    @Min(value = 10, message = "Participant number should be greater than 10.")
    @Max(value = 10000, message = "Participant number should be less than 10000.")
    private int maxNumOfParticipants;
}
