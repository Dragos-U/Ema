package ro.itschool.ema.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.itschool.ema.exceptions.ParticipantCreateException;
import ro.itschool.ema.models.dtos.ParticipantDTO;
import ro.itschool.ema.models.entities.Participant;
import ro.itschool.ema.repositories.ParticipantRepository;


@Slf4j
@Service
public class ParticipantServiceImpl implements ParticipantService {


    private final ParticipantRepository participantRepository;
    private final ObjectMapper objectMapper;

    public ParticipantServiceImpl(ParticipantRepository participantRepository, ObjectMapper objectMapper) {
        this.participantRepository = participantRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public ParticipantDTO createParticipant(ParticipantDTO participantDTO) {
        Participant participantEntity = objectMapper.convertValue(participantDTO, Participant.class);

        if (participantRepository.existsByPhoneNumber(participantEntity.getPhoneNumber())) {
            throw new ParticipantCreateException("Participant already exists by phone number");
        }

        Participant saveParticipantEntity = participantRepository.save(participantEntity);
        return objectMapper.convertValue(saveParticipantEntity, ParticipantDTO.class);
    }

    @Override
    public boolean deleteParticipantById(Long id) {
        if (participantRepository.existsById(id)) {
            participantRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
