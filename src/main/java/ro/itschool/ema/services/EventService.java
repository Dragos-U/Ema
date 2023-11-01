package ro.itschool.ema.services;

import ro.itschool.ema.models.dtos.EventDTO;

import java.util.List;

public interface EventService {

    EventDTO createEvent(EventDTO eventDTO);
    EventDTO getEventById(Long id);
    List<EventDTO> getUpcomingEvents();
    EventDTO updateEvent(Long id, EventDTO eventDTO);
}
