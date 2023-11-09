package ro.itschool.ema.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import ro.itschool.ema.models.dtos.AddressDTO;
import ro.itschool.ema.models.dtos.ParticipantDTO;
import ro.itschool.ema.services.EmailService;
import ro.itschool.ema.services.ParticipantService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class ParticipantControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParticipantService participantService;

    @MockBean
    private EmailService emailService;

    @Test
    @DisplayName("When adding a participant to an event, return status 201")
    void whenAddParticipantToEvent() throws Exception {
        String participantJson = """
                    {
                        "name": "John Doe",
                        "email": "john.doe@example.com",
                        "phoneNumber": "+401234567890",
                        "address": {
                            "street": "123 Main St",
                            "city": "Anytown",
                            "postalCode": "300400",
                            "country": "Fantasia"
                        },
                        "events": [1]
                    }
                """;
        ParticipantDTO mockParticipantDTO = new ParticipantDTO();
        mockParticipantDTO.setName("John Doe");
        mockParticipantDTO.setEmail("john.doe@example.com");
        mockParticipantDTO.setPhoneNumber("+401234567890");

        given(participantService.createAndAddParticipantToEvent(eq(1L), any(ParticipantDTO.class))).willReturn(mockParticipantDTO);

        mockMvc.perform(post("/api/events/1/participants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(participantJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"))
                .andExpect(jsonPath("$.phoneNumber").value("+401234567890")).andReturn();
    }

    @Test
    @DisplayName("Get participants for eventID and return status 200")
    void whenGetParticipantsForEvent() throws Exception {
        AddressDTO address = new AddressDTO();
        address.setStreet("123 Main St");
        address.setCity("Anytown");
        address.setPostalCode(300400);
        address.setCountry("Fantasia");

        ParticipantDTO participantDTO = new ParticipantDTO();
        participantDTO.setId(1L);
        participantDTO.setName("John Doe");
        participantDTO.setEmail("john.doe@example.com");
        participantDTO.setPhoneNumber("+401234567890");
        participantDTO.setAddress(address);

        List<ParticipantDTO> participantDTOList = List.of(participantDTO);

        given(participantService.getParticipantsForEvent(eq(1L))).willReturn(participantDTOList);

        mockMvc.perform(get("/api/events/1/participants")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(participantDTO.getId()))
                .andExpect(jsonPath("$[0].name").value(participantDTO.getName()))
                .andExpect(jsonPath("$[0].email").value(participantDTO.getEmail()))
                .andExpect(jsonPath("$[0].phoneNumber").value(participantDTO.getPhoneNumber()))
                .andExpect(jsonPath("$[0].address.street").value(participantDTO.getAddress().getStreet()))
                .andExpect(jsonPath("$[0].address.city").value(participantDTO.getAddress().getCity()))
                .andExpect(jsonPath("$[0].address.postalCode").value(participantDTO.getAddress().getPostalCode()))
                .andExpect(jsonPath("$[0].address.country").value(participantDTO.getAddress().getCountry()));
    }
}
