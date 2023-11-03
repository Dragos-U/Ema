package ro.itschool.ema.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ro.itschool.ema.exceptions.ParticipantCreateException;
import ro.itschool.ema.models.dtos.ParticipantDTO;
import ro.itschool.ema.models.entities.Address;
import ro.itschool.ema.models.entities.Participant;
import ro.itschool.ema.repositories.AddressRepository;
import ro.itschool.ema.repositories.ParticipantRepository;



@Slf4j
@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

    private static final Logger logger = LoggerFactory.getLogger(ParticipantServiceImpl.class);
    public static final String LOCALHOST_8080 = "http://localhost:8080/api/events/{eventId}/participants";

    private final ParticipantRepository participantRepository;
    private final AddressRepository addressRepository;
    private final ObjectMapper objectMapper;
    private final EmailService emailService;

    @Override
    @Transactional
    public ParticipantDTO createParticipant(ParticipantDTO participantDTO) {
        String participantDTOEmail = participantDTO.getEmail();
        Long participantDTOId = participantDTO.getId();

        if (participantRepository.existsByName(participantDTO.getName())) {
            throw new ParticipantCreateException("Participant already exists.");
        }

        logger.info("> Sending email to: " + participantDTOEmail);
        String accountLink = LOCALHOST_8080 + participantDTOId;
        emailService.sendEmail(participantDTO, accountLink);
        Participant participantEntity = objectMapper.convertValue(participantDTO, Participant.class);

        if (participantEntity.getAddress() != null) {
            Address addressEntity = addressRepository.save(participantEntity.getAddress());
            participantEntity.setAddress(addressEntity);
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
