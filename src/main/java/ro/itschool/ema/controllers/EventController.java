package ro.itschool.ema.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.itschool.ema.models.dtos.EventDTO;
import ro.itschool.ema.services.EventService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @PostMapping("/events")
    public ResponseEntity<EventDTO> createEvent(@RequestBody @Valid EventDTO eventDTO) {
        return ResponseEntity.ok(eventService.createEvent(eventDTO));
    }

    @GetMapping("/events")
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        List<EventDTO> allEvents = eventService.getAllEvents();
        if (allEvents.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(allEvents);
        }
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable long id) {
        EventDTO eventDTO = eventService.getEventById(id);
        if (eventDTO == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(eventDTO);
        }
    }

    @GetMapping("/events/upcoming")
    public ResponseEntity<List<EventDTO>> getUpcomingEvents() {
        List<EventDTO> upcomingEvents = eventService.getUpcomingEvents();
        if (upcomingEvents.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(upcomingEvents);
        }
    }

    @GetMapping("/events/upcoming/{city}/{country}")
    public ResponseEntity<Set<EventDTO>> getUpcomingEventsByLocation(@PathVariable String city, @PathVariable String country) {
        List<EventDTO> upcomingEvents = eventService.getUpcomingEvents();
        Set<EventDTO> upcomingEventsByLocation = eventService.getUpcomingEventsByLocation(upcomingEvents, city, country);
        if (upcomingEventsByLocation.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(upcomingEventsByLocation);
        }
    }

    @PutMapping("/events/{id}")
    public ResponseEntity<EventDTO> updateEvent(@PathVariable Long id, @RequestBody EventDTO eventDTO) {
        EventDTO updateEventDTO = eventService.updateEvent(id, eventDTO);
        return ResponseEntity.ok().body(updateEventDTO);
    }
}
