package ro.itschool.ema.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.itschool.ema.exceptions.EventNotFoundException;
import ro.itschool.ema.exceptions.EventUpdateException;
import ro.itschool.ema.models.dtos.EventDTO;
import ro.itschool.ema.models.entities.Event;
import ro.itschool.ema.repositories.EventRepository;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;

    @Override
    public EventDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + id));
        return convertToDTO(event);
    }

    @Override
    public List<EventDTO> getUpcomingEvents() {
        LocalDateTime now = LocalDateTime.now();
        return eventRepository.findAll().stream()
                .filter(event -> event.getEventDate().isAfter(ChronoLocalDate.from(now)))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        try {
            Event updatedEvent = eventRepository.findById(id)
                    .map(event -> updateEventDetails(event, eventDTO))
                    .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + id));

            Event savedEvent = eventRepository.save(updatedEvent);
            return convertToDTO(savedEvent);
        } catch (EventNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (Exception e) {
            throw new EventUpdateException("Failed to update event with id: " + id, e);
        }
    }

    public Event updateEventDetails(Event event, EventDTO eventDTO){
        event.setEventName(eventDTO.getEventName());
        event.setEventDate(eventDTO.getEventDate());
        event.setStartTime(eventDTO.getStartTime());
        event.setEndTime(eventDTO.getEndTime());
        event.setEventVenue(eventDTO.getEventVenue());
        event.setEventDescription(eventDTO.getEventDescription());
        event.setMaxNumOfParticipants(eventDTO.getMaxNumOfParticipants());
        return event;
    }

    private EventDTO convertToDTO(Event event){
        return objectMapper.convertValue(event, EventDTO.class);
    }
}
