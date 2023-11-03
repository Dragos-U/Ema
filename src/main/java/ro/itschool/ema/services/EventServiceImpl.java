package ro.itschool.ema.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ro.itschool.ema.exceptions.EventCreateException;
import ro.itschool.ema.exceptions.EventNotFoundException;
import ro.itschool.ema.exceptions.EventUpdateException;
import ro.itschool.ema.exceptions.OrganizerNotFoundException;
import ro.itschool.ema.models.dtos.EventDTO;
import ro.itschool.ema.models.dtos.OrganizerDTO;
import ro.itschool.ema.models.entities.Address;
import ro.itschool.ema.models.entities.Event;
import ro.itschool.ema.models.entities.Organizer;
import ro.itschool.ema.repositories.AddressRepository;
import ro.itschool.ema.repositories.EventRepository;
import ro.itschool.ema.repositories.OrganizerRepository;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final AddressRepository addressRepository;
    private final OrganizerRepository organizerRepository;
    private final ObjectMapper objectMapper;

    @Override
    @Transactional
    public EventDTO createEvent(EventDTO eventDTO) {
        if (!eventDTO.getStartTime().isBefore(eventDTO.getEndTime())) {
            throw new EventCreateException("Event start time must be before event end time.");
        }

        Event eventEntity = objectMapper.convertValue(eventDTO, Event.class);

        Organizer organizer = getOrganizer(eventDTO.getOrganizerId());

        Set<Organizer> organizers = eventEntity.getOrganizers();
        organizers.add(organizer);
        eventEntity.setOrganizers(organizers);

        addEventToList(eventEntity, organizer);

        if (eventEntity.getAddress() != null) {
            Address addressEntity = addressRepository.save(eventEntity.getAddress());
            eventEntity.setAddress(addressEntity);
        }

        Event eventResponseEntity = eventRepository.save(eventEntity);

        Set<OrganizerDTO> organizerDTOSet = new HashSet<>();
        for (Organizer organizerEntity : organizers) {
            organizerDTOSet.add(objectMapper.convertValue(organizerEntity, OrganizerDTO.class));
        }
        OrganizerDTO organizerDTOResponse = objectMapper.convertValue(organizer, OrganizerDTO.class);
        EventDTO eventDTOResponse = objectMapper.convertValue(eventResponseEntity, EventDTO.class);

        eventDTOResponse.setOrganizerId(organizerDTOResponse.getId());

        eventDTOResponse.setOrganizers(organizerDTOSet);

        return eventDTOResponse;
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

    @Override
    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        List<EventDTO> eventDTOList = new ArrayList<>();

        for (Event event : events) {
            EventDTO eventDTO = objectMapper.convertValue(event, EventDTO.class);
            Set<OrganizerDTO> organizerDTOSet = new HashSet<>();

            for (Organizer organizer : event.getOrganizers()) {
                OrganizerDTO organizerDTO = objectMapper.convertValue(organizer, OrganizerDTO.class);
                organizerDTOSet.add(organizerDTO);
            }
            eventDTO.setOrganizers(organizerDTOSet);
            eventDTOList.add(eventDTO);
        }
        return eventDTOList;
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

    private Organizer getOrganizer(Long organizerId){
        return organizerRepository.findById(organizerId)
                .orElseThrow(() -> new OrganizerNotFoundException("Could not find specified organization"));
    }

    private void addEventToList(Event event, Organizer organizer){
        Set<Event> events = organizer.getEvents();
        events.add(event);
    }
}
