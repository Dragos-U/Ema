package ro.itschool.ema.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
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
import ro.itschool.ema.models.entities.Event;
import ro.itschool.ema.models.entities.Participant;
import ro.itschool.ema.repositories.AddressRepository;
import ro.itschool.ema.repositories.EventRepository;
import ro.itschool.ema.repositories.ParticipantRepository;

import java.util.Optional;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParticipantServiceImplTest {

    @Mock
    private ParticipantRepository participantRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private ParticipantServiceImpl participantServiceImpl;

    @Test
    @DisplayName("Participant created and added successfully to an event")
    void addParticipantToEventSuccess() {
        // Given
        Long eventId = 1L;
        Event event = new Event();

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

        Participant participant = new Participant();
        participant.setName(participantDTO.getName());
        participant.setEmail(participantDTO.getEmail());
        participant.setPhoneNumber(participantDTO.getPhoneNumber());

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(objectMapper.convertValue(participantDTO, Participant.class)).thenReturn(participant);
        when(participantRepository.save(participant)).thenReturn(participant);
        when(objectMapper.convertValue(participant, ParticipantDTO.class)).thenReturn(participantDTO);

        doNothing().when(emailService).sendEmail(any(ParticipantDTO.class), anyString());

        // When
        ParticipantDTO savedParticipantDTO = participantServiceImpl.createAndAddParticipantToEvent(eventId, participantDTO);

        // Then
        verify(participantRepository, times(2)).save(participant);
        verify(eventRepository, times(1)).save(event);
        assertEquals(participantDTO.getName(), savedParticipantDTO.getName());
        assertEquals(participantDTO.getEmail(), savedParticipantDTO.getEmail());
    }

    @Test
    @Disabled
    @DisplayName("Error occurs when participant has a null address.")
    void participantAddressIsNull() {
        // Given
        Long eventId = 1L;
        Event event = new Event(); // Assuming you have a default constructor

        ParticipantDTO participantDTO = new ParticipantDTO();
        participantDTO.setId(1L);
        participantDTO.setName("Dumitru Alex");
        participantDTO.setEmail("dum.alex@gmail.com");
        participantDTO.setPhoneNumber("0471-562-921");

        Participant participant = new Participant();
        participant.setName(participantDTO.getName());
        participant.setEmail(participantDTO.getEmail());
        participant.setPhoneNumber(participantDTO.getPhoneNumber());
        participant.setAddress(null);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(objectMapper.convertValue(participantDTO, Participant.class)).thenReturn(participant);

        // When
        ParticipantDTO savedParticipantDTO = participantServiceImpl.createAndAddParticipantToEvent(eventId, participantDTO);

        // Then
        assertNull(savedParticipantDTO.getAddress(), "Address should be null in the returned ParticipantDTO");
    }

    @Test
    @DisplayName("Event not found exception is thrown.")
    void eventNotFoundTestThrowsException() {
        Long eventId = 1L;
        ParticipantDTO participantDTO = new ParticipantDTO();
        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());
        Assertions.assertThrows(EventNotFoundException.class, () -> participantServiceImpl.createAndAddParticipantToEvent(eventId, participantDTO));
    }
}

