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
import ro.itschool.ema.models.dtos.AddressDTO;
import ro.itschool.ema.models.dtos.EventDTO;
import ro.itschool.ema.models.dtos.OrganizerDTO;
import ro.itschool.ema.models.entities.Address;
import ro.itschool.ema.models.entities.Event;
import ro.itschool.ema.models.entities.Organizer;
import ro.itschool.ema.repositories.AddressRepository;
import ro.itschool.ema.repositories.EventRepository;
import ro.itschool.ema.repositories.OrganizerRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.*;
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

        Set<Organizer> organizerList = getOrganizer(eventDTO.getOrganizerIds());
        eventEntity.setOrganizers(organizerList);
        addEventToList(eventEntity, organizerList);

        if (eventEntity.getAddress() != null) {
            Address addressEntity = addressRepository.save(eventEntity.getAddress());
            eventEntity.setAddress(addressEntity);
        }

        Event eventResponseEntity = eventRepository.save(eventEntity);

        Set<OrganizerDTO> organizerDTOSet = getOrganizerDTOSet(organizerList);
        long[] organizerIds = getOrganizerIds(organizerDTOSet);

        EventDTO eventDTOResponse = convertToDTO(eventResponseEntity);
        eventDTOResponse.setOrganizers(organizerDTOSet);
        eventDTOResponse.setOrganizerIds(organizerIds);
        return eventDTOResponse;
    }

    @Override
    public EventDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Event not found with id: " + id));
        Set<OrganizerDTO> organizerDTOSet = getOrganizerDTOSet(event.getOrganizers());
        long[] organizerIds = getOrganizerIds(organizerDTOSet);

        EventDTO eventDTO = convertToDTO(event);
        eventDTO.setOrganizers(organizerDTOSet);
        eventDTO.setOrganizerIds(organizerIds);
        return eventDTO;
    }

    @Override
    public List<EventDTO> getAllEvents() {
        List<Event> events = eventRepository.findAll();
        List<EventDTO> eventDTOList = new ArrayList<>();

        for (Event event : events) {
            if (event.getEventDate().isAfter(ChronoLocalDate.from(LocalDateTime.now()))) {
                List<Long> organizerIDs = new ArrayList<>();
                EventDTO eventDTO = objectMapper.convertValue(event, EventDTO.class);
                Set<OrganizerDTO> organizerDTOSet = new HashSet<>();

                for (Organizer organizer : event.getOrganizers()) {
                    organizerIDs.add(organizer.getId());
                    OrganizerDTO organizerDTO = objectMapper.convertValue(organizer, OrganizerDTO.class);
                    organizerDTOSet.add(organizerDTO);
                }
                long[] orgIds = organizerIDs.stream().mapToLong(i -> i).toArray();
                eventDTO.setOrganizers(organizerDTOSet);
                eventDTO.setOrganizerIds(orgIds);
                eventDTOList.add(eventDTO);
            }
        }
        return eventDTOList;
    }

    @Override
    public List<EventDTO> getUpcomingEvents() {
        List<Event> events = eventRepository.findAll();
        List<EventDTO> eventDTOList = new ArrayList<>();

        for (Event event : events) {
            if (event.getEventDate().isAfter(ChronoLocalDate.from(LocalDateTime.now()))){
                Set<OrganizerDTO> organizerDTOSet = getOrganizerDTOSet(event.getOrganizers());
                long[] organizersIds = getOrganizerIds(organizerDTOSet);

                EventDTO eventDTO = convertToDTO(event);
                eventDTO.setOrganizers(organizerDTOSet);
                eventDTO.setOrganizerIds(organizersIds);
                eventDTOList.add(eventDTO);
            }
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

    @Override
    public Set<EventDTO> getEventsByDate(List<EventDTO> eventDTOList, LocalDate date) {
        try {
            Set<EventDTO> eventSet = eventDTOList
                    .stream()
                    .filter(event -> event.getEventDate().equals(date))
                    .collect(Collectors.toSet());
            if (eventSet.isEmpty()) {
                throw new EventNotFoundException("Can't find events for specified date.");
            }
            return eventSet;
        } catch (EventNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, e.getMessage(), e);
        }
    }

    @Override
    public void deleteEvent(Long id) {
        try {
            Event event = eventRepository.findById(id)
                    .orElseThrow(() -> new EventNotFoundException("Event not found with id:" + id));
            eventRepository.delete(event);
        } catch (EventNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @Override
    public Set<EventDTO> sortEventsByDate() {
        try{
            List<Event> events = eventRepository.findAll();
            Set<EventDTO> eventDTOSet = new HashSet<>();

            for (Event event : events) {
                eventDTOSet.add(convertToDTO(event));
            }

            Set<EventDTO> sortedByDates = eventDTOSet
                    .stream()
                    .sorted(Comparator.comparing(EventDTO::getEventDate))
                    .collect(Collectors.toCollection(LinkedHashSet::new));

            if(sortedByDates.isEmpty()){
                throw new EventNotFoundException("Event dates cannot be sorted because they do not exist.");
            }
            return sortedByDates;
        } catch (EventNotFoundException e) {

            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
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

    private Set<Organizer> getOrganizer(long[] organizerIds){
        Set<Organizer> organizers = new HashSet<>();
        for (long organizerId : organizerIds) {
            Optional<Organizer> organizerOptional = organizerRepository.findById(organizerId);
            Organizer organizer = organizerOptional.orElseThrow(() -> new OrganizerNotFoundException("Could not find specified organization"));
            organizers.add(organizer);
        }
        return organizers;
    }

    private Set<OrganizerDTO> getOrganizerDTOSet(Set<Organizer> organizers) {
        return organizers.stream()
                .map(organizer -> objectMapper.convertValue(organizer, OrganizerDTO.class))
                .collect(Collectors.toSet());
    }

    private long[] getOrganizerIds(Set<OrganizerDTO> organizerDTOSet){
        return organizerDTOSet.stream()
                .mapToLong(OrganizerDTO::getId)
                .toArray();
    }
    private void addEventToList(Event event, Set<Organizer> organizers){
        for (Organizer organizer : organizers) {
            Set<Event> events = organizer.getEvents();
            events.add(event);
        }
    }

    public Set<EventDTO> getUpcomingEventsByLocation(List<EventDTO> eventDTOList, String city, String country) {
        try {
            Set<EventDTO> upcomingEventsByLocation = eventDTOList.stream()
                    .filter(eventDTO -> eventDTO.getAddress().getCity().equalsIgnoreCase(city)
                            && eventDTO.getAddress().getCountry().equalsIgnoreCase(country))
                    .collect(Collectors.toSet());
            if (upcomingEventsByLocation.isEmpty()) {
                throw new EventNotFoundException("No events in the specified Location.");
            }
            return upcomingEventsByLocation;
        } catch (EventNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
