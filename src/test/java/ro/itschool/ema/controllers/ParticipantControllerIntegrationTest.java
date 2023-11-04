package ro.itschool.ema.controllers;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ro.itschool.ema.models.dtos.AddressDTO;
import ro.itschool.ema.models.dtos.ParticipantDTO;
import ro.itschool.ema.services.ParticipantService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@AutoConfigureTestDatabase
class ParticipantControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateParticipantShouldPass() throws Exception {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setStreet("Aleea Adjud, Sector 3");
        addressDTO.setCity("Bucuresti");
        addressDTO.setPostalCode(132736);
        addressDTO.setCountry("Romania");

        ParticipantDTO participantDTO = new ParticipantDTO();
        participantDTO.setName("Dumitru Alex");
        participantDTO.setEmail("dum.alex@gmail.com");
        participantDTO.setPhoneNumber("0471-562-921");
        participantDTO.setAddress(addressDTO);

        MvcResult result = mockMvc.perform(post("/api/events/1/participants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(participantDTO)))
                        .andExpect(status().isOk())
                        .andReturn();

        String resultAsString = result.getResponse().getContentAsString();
        ParticipantDTO convertedParticipantDTO = objectMapper.readValue(resultAsString, new TypeReference<ParticipantDTO>() {
        });

        assertEquals(convertedParticipantDTO.getName(), participantDTO.getName());

    }

}
