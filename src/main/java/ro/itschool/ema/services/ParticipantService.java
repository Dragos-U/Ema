package ro.itschool.ema.services;

import ro.itschool.ema.models.dtos.ParticipantDTO;

import java.util.List;

public interface ParticipantService {

    ParticipantDTO createParticipant(ParticipantDTO participantDTO);



    boolean deleteParticipantById(Long id);

}
