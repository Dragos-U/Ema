package ro.itschool.ema.services;

import ro.itschool.ema.models.dtos.EventDTO;

import java.util.List;
import java.util.Set;

public interface EventService {

    EventDTO createEvent(EventDTO eventDTO);
    EventDTO getEventById(Long id);
    List<EventDTO> getUpcomingEvents();
    Set<EventDTO> getUpcomingEventsByLocation(List<EventDTO> eventDTOList, String city, String country);
    List<EventDTO> getAllEvents();
    EventDTO updateEvent(Long id, EventDTO eventDTO);
}
