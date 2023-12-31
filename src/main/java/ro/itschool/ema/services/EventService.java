package ro.itschool.ema.services;

import ro.itschool.ema.models.dtos.EventDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface EventService {

    EventDTO createEvent(EventDTO eventDTO);
    EventDTO getEventById(Long id);
    List<EventDTO> getUpcomingEvents();
    Set<EventDTO> getUpcomingEventsByLocation(List<EventDTO> eventDTOList, String city, String country);
    List<EventDTO> getAllEvents();
    Set<EventDTO> getEventsByDate(List<EventDTO> eventDTOList, LocalDate date);
    Set<EventDTO> sortEventsByDate();
    EventDTO updateEvent(Long id, EventDTO eventDTO);
    void deleteEvent(Long id);
}
