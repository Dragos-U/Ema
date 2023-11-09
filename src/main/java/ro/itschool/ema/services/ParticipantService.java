package ro.itschool.ema.services;

import ro.itschool.ema.models.dtos.ParticipantDTO;

import java.util.List;

public interface ParticipantService {

    boolean deleteParticipantById(Long id);
    List<ParticipantDTO> getParticipantsForEvent(Long eventId);
    ParticipantDTO createAndAddParticipantToEvent(Long eventId, ParticipantDTO participantDTO);
}
