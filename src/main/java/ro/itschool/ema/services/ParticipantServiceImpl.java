package ro.itschool.ema.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.itschool.ema.exceptions.EventNotFoundException;
import ro.itschool.ema.exceptions.ParticipantCreateException;
import ro.itschool.ema.exceptions.ParticipantNotFoundException;
import ro.itschool.ema.models.dtos.ParticipantDTO;
import ro.itschool.ema.models.entities.Event;
import ro.itschool.ema.models.entities.Participant;
import ro.itschool.ema.repositories.EventRepository;
import ro.itschool.ema.repositories.ParticipantRepository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    private static final Logger logger = LoggerFactory.getLogger(ParticipantServiceImpl.class);
    public static final String LOCALHOST_8080 = "http://localhost:8080/api/events/{eventId}/participants";

    private final ParticipantRepository participantRepository;
    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    @Override
    @Transactional
    public ParticipantDTO createAndAddParticipantToEvent(Long eventId, ParticipantDTO participantDTO) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event not found"));
        Participant participant = objectMapper.convertValue(participantDTO, Participant.class);
        participant = participantRepository.save(participant);

        event.getParticipants().add(participant);
        participant.getEvents().add(event);
        eventRepository.save(event);
        participantRepository.save(participant);

        sendEmail(participantDTO);

        return objectMapper.convertValue(participant, ParticipantDTO.class);
    }

    @Override
    @Transactional
    public List<ParticipantDTO> getParticipantsForEvent(Long eventId) {
        List<Participant> participants = participantRepository.findAllByEventId(eventId);
        if (eventRepository.existsById(eventId) && !participants.isEmpty()) {
            return participants.stream()
                    .map(participant -> objectMapper.convertValue(participant, ParticipantDTO.class))
                    .collect(Collectors.toList());
        } else if (eventRepository.existsById(eventId) && participants.isEmpty()) {
            throw new ParticipantNotFoundException("No participants found for this event.");
        } else {
            throw new EventNotFoundException("Event not found.");
        }
    }

    @Override
    public boolean deleteParticipantById(Long id) {
        if (participantRepository.existsById(id)) {
            participantRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void sendEmail(ParticipantDTO participantDTO) {
        String participantDTOEmail = participantDTO.getEmail();
        Long participantDTOId = participantDTO.getId();
        logger.info("> Sending email to: " + participantDTOEmail);
        String accountLink = LOCALHOST_8080 + participantDTOId;
        emailService.sendEmail(participantDTO, accountLink);
    }
}
