package ro.itschool.ema.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.itschool.ema.exceptions.EventNotFoundException;
import ro.itschool.ema.exceptions.ParticipantCreateException;
import ro.itschool.ema.exceptions.ParticipantNotFoundException;
import ro.itschool.ema.models.dtos.AddressDTO;
import ro.itschool.ema.models.dtos.ParticipantDTO;
import ro.itschool.ema.models.entities.Address;
import ro.itschool.ema.models.entities.Participant;
import ro.itschool.ema.repositories.AddressRepository;
import ro.itschool.ema.repositories.EventRepository;
import ro.itschool.ema.repositories.ParticipantRepository;

import java.util.Optional;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceImplTest {

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private ParticipantServiceImpl participantServiceImpl;

    @Test
    @DisplayName("Participant created successfully")
    void createParticipantTest(){
        //given
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(1L);
        addressDTO.setStreet("Aleea Adjud, Sector 3");
        addressDTO.setCity("Bucuresti");
        addressDTO.setPostalCode(032736);
        addressDTO.setCountry("Romania");

        ParticipantDTO participantDTO = new ParticipantDTO();
        participantDTO.setId(1L);
        participantDTO.setName("Dumitru Alex");
        participantDTO.setEmail("dum.alex@gmail.com");
        participantDTO.setPhoneNumber("0471-562-921");
        participantDTO.setAddress(addressDTO);

        Participant participant = new Participant();
        participant.setId(1L);
        participant.setName("Dumitru Alex");
        participant.setEmail("dum.alex@gmail.com");
        participant.setPhoneNumber("0471-562-921");

        Address address = new Address();
        address.setId(1L);
        address.setStreet("Aleea Adjud, Sector 3");
        address.setCity("Bucuresti");
        address.setPostalCode(032736);
        address.setParticipant(participant);

        Participant savedParticipantEntity = new Participant();
        savedParticipantEntity.setId(1L);
        savedParticipantEntity.setName("Dumitru Alex");
        savedParticipantEntity.setEmail("dum.alex@gmail.com");
        savedParticipantEntity.setPhoneNumber("0471-562-921");

        Address savedAddressEntity = new Address();
        savedAddressEntity.setId(1L);
        savedAddressEntity.setStreet("Aleea Adjud, Sector 3");
        savedAddressEntity.setCity("Bucuresti");
        savedAddressEntity.setPostalCode(032736);
        savedAddressEntity.setParticipant(savedParticipantEntity);

        when(objectMapper.convertValue(participantDTO, Participant.class)).thenReturn(participant);
        when(participantRepository.save(participant)).thenReturn(savedParticipantEntity);
        when(objectMapper.convertValue(savedParticipantEntity, ParticipantDTO.class)).thenReturn(participantDTO);
        //when

//        ParticipantDTO savedParticipant = participantServiceImpl.createParticipant(participantDTO);

        //test
//        Assertions.assertEquals(participantDTO, savedParticipant);

    }

    @Test
    @DisplayName("Throw exception if participant already exists by email")
    void createParticipantAlreadyExists() {
        ParticipantDTO participantDTO = new ParticipantDTO();
        participantDTO.setEmail("mihai@yahoo.com");

//        when(participantRepository.existsByEmail("mihai@yahoo.com")).thenReturn(true);
//        assertThrows(ParticipantCreateException.class, () -> participantServiceImpl.createParticipant(participantDTO));
    }


    @Test
    @DisplayName("Error occurs when participant has a null address.")
    void participantAddressIsNull(){
        ParticipantDTO participantDTO = new ParticipantDTO();
        participantDTO.setAddress(null);

//        when(participantServiceImpl.createParticipant(participantDTO).getAddress() == null).thenReturn(true);
//        assertNull(participantDTO.getAddress());
    }

    @Test
    @DisplayName("Event not found exception is thrown.")
    void eventNotFoundTestThrowsException() {
        ParticipantDTO participantDTO = new ParticipantDTO();
        Long eventId = 1L;
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());
        Assertions.assertThrows(EventNotFoundException.class, () -> participantServiceImpl.createAndAddParticipantToEvent(eventId, participantDTO));
    }
}

