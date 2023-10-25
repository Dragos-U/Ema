package ro.itschool.ema.models.dtos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class EventDTO implements Serializable {
    private Long id;
    private String eventName;
    private Date eventDate;
    private String eventVenue;
    private String eventDescription;
    private int maxNumOfParticipants;
}
