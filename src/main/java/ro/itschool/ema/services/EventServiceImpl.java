package ro.itschool.ema.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.itschool.ema.exceptions.EventNotFoundException;
import ro.itschool.ema.models.dtos.EventDTO;
import ro.itschool.ema.models.entities.Event;
import ro.itschool.ema.repositories.EventRepository;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;

    public EventServiceImpl(EventRepository eventRepository, ObjectMapper objectMapper) {
        this.eventRepository = eventRepository;
        this.objectMapper = objectMapper;
    }

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

    private EventDTO convertToDTO(Event event){
        return objectMapper.convertValue(event, EventDTO.class);
    }
}
