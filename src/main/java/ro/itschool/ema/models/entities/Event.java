package ro.itschool.ema.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_name")
    @NotNull(message = "Field cannot be null.")
    private String eventName;

    @Column(name = "event_date")
    @NotNull(message = "Field cannot be null.")
    private Date eventDate;

    @Column(name = "event_venue")
    @NotNull(message = "Field cannot be null.")
    private String eventVenue;

    @Column(name = "event_description")
    @NotNull(message = "Field cannot be null.")
    private String eventDescription;

    @Column(name = "max_num_participants")
    @NotNull(message = "Field cannot be null.")
    @Min(value = 10, message = "Participant number should be greater than 10.")
    @Max(value = 10000, message = "Participant number should be less than 10000.")
    private int maxNumOfParticipants;
}
