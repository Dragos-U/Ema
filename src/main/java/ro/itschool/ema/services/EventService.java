package ro.itschool.ema.services;

import ro.itschool.ema.models.dtos.EventDTO;

import java.util.List;

public interface EventService {

    EventDTO getEventById(Long id);
    List<EventDTO> getUpcomingEvents();
}
