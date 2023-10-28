package ro.itschool.ema.controllers;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.itschool.ema.models.dtos.EventDTO;
import ro.itschool.ema.services.EventService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/events/{id}")
    public ResponseEntity<EventDTO> getEventById(@PathVariable long id){
        EventDTO eventDTO = eventService.getEventById(id);
        if(eventDTO.equals(null)){
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(eventDTO);
        }
    }

    @GetMapping("/events/upcoming")
    public ResponseEntity<List<EventDTO>> getUpcomingEvents(){
        List<EventDTO> upcomingEvents = eventService.getUpcomingEvents();
        if(upcomingEvents.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(upcomingEvents);
        }
    }
}