package ro.itschool.ema.services;

import ro.itschool.ema.models.dtos.EventDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface EventService {

    EventDTO createEvent(EventDTO eventDTO);
    EventDTO getEventById(Long id);
    List<EventDTO> getUpcomingEvents();
    List<EventDTO> getAllEvents();
    Set<EventDTO> getEventsByDate(List<EventDTO> eventDTOList, LocalDate date);
    EventDTO updateEvent(Long id, EventDTO eventDTO);
}
