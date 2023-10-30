package ro.itschool.ema.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.itschool.ema.exceptions.EventNotFoundException;
import ro.itschool.ema.models.dtos.EventDTO;
import ro.itschool.ema.models.entities.Event;
import ro.itschool.ema.repositories.EventRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    @DisplayName("Event retrieved successfully by id")
    void getEventByIdTest(){
        Long id = 1L;
        Event event = new Event();
        event.setId(id);

        EventDTO eventDTO = new EventDTO();
        eventDTO.setEventName("Event1");
        eventDTO.setEventDate(LocalDate.now());

        when(eventRepository.findById(id)).thenReturn(Optional.of(event));
        when(objectMapper.convertValue(event, EventDTO.class)).thenReturn(eventDTO);

        EventDTO result = eventService.getEventById(id);
        assertNotNull(result);
        assertEquals(eventDTO,result);
    }

    @Test
    @DisplayName("Throw exception if event not found")
    void getEventByIdNotFound() {
        Long id = 1L;
        when(eventRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(EventNotFoundException.class, () -> eventService.getEventById(id));
    }

    @Test
    @DisplayName("Upcoming events are retrieved successfully")
    void getUpcomingEvents() {
        Event pastEvent = new Event();
        Event futureEvent1 = new Event();
        Event futureEvent2 = new Event();
        pastEvent.setEventDate(LocalDate.now().minusDays(1));
        futureEvent1.setEventDate(LocalDate.now().plusDays(1));
        futureEvent2.setEventDate(LocalDate.now().plusDays(2));

        List<Event> allEvents = Arrays.asList(pastEvent, futureEvent1, futureEvent2);

        when(eventRepository.findAll()).thenReturn(allEvents);

        EventDTO futureEventDTO1 = new EventDTO();
        futureEventDTO1.setEventName("Future Event 1");
        futureEventDTO1.setEventDate(futureEvent1.getEventDate());
        EventDTO futureEventDTO2 = new EventDTO();
        futureEventDTO2.setEventName("Future Event 2");
        futureEventDTO2.setEventDate(futureEvent2.getEventDate());

        when(objectMapper.convertValue(futureEvent1, EventDTO.class)).thenReturn(futureEventDTO1);
        when(objectMapper.convertValue(futureEvent2, EventDTO.class)).thenReturn(futureEventDTO2);

        List<EventDTO> upcomingEvents = eventService.getUpcomingEvents();

        assertEquals(2, upcomingEvents.size());
        assertTrue(upcomingEvents.contains(futureEventDTO1));
        assertTrue(upcomingEvents.contains(futureEventDTO2));
    }
}
