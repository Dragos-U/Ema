package ro.itschool.ema.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ro.itschool.ema.models.dtos.AddressDTO;
import ro.itschool.ema.models.dtos.OrganizerDTO;
import ro.itschool.ema.services.OrganizerService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrganizerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrganizerService organizerService;

    @Test
    @DisplayName("When adding a new organizer, return status code 201.")
    void whenCreateOrganizerTest() throws Exception {
        String organizerJson = """
                {
                            "organizerName": "Organizer Name",
                            "description": "type",
                            "website": "https://www.organizer.com/",
                            "phoneNumber": "0712345678",
                            "address":{
                                "street": "Street",
                                "city": "City",
                                "postalCode": "123",
                                "country": "Country"
                            }
                }
                """;
        OrganizerDTO mockOrganizerDTO = new OrganizerDTO();
        mockOrganizerDTO.setOrganizerName("Organizer Name");
        mockOrganizerDTO.setWebsite("https://www.organizer.com/");
        mockOrganizerDTO.setPhoneNumber("0712345678");

        BDDMockito.given(organizerService.createOrganizer(any(OrganizerDTO.class))).willReturn(mockOrganizerDTO);
        mockMvc.perform(post("/api/organizers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(organizerJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.website").value("https://www.organizer.com/")).andReturn();
    }

    @Test
    @DisplayName("Get organizer list and return status code 200.")
    void whenGetOrganizerList() throws Exception {
        OrganizerDTO organizerDTO = createOrganizerDTO();

        List<OrganizerDTO> organizerDTOList = List.of(organizerDTO);
        BDDMockito.given(organizerService.getAllOrganizers()).willReturn(organizerDTOList);

        mockMvc.perform(get("/api/organizers")
                    .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(organizerDTO.getId()))
                .andExpect(jsonPath("$[0].organizerName").value(organizerDTO.getOrganizerName()))
                .andExpect(jsonPath("$[0].description").value(organizerDTO.getDescription()))
                .andExpect(jsonPath("$[0].website").value(organizerDTO.getWebsite()))
                .andExpect(jsonPath("$[0].phoneNumber").value(organizerDTO.getPhoneNumber()))
                .andExpect(jsonPath("$[0].address.street").value(organizerDTO.getAddress().getStreet()))
                .andExpect(jsonPath("$[0].address.city").value(organizerDTO.getAddress().getCity()))
                .andExpect(jsonPath("$[0].address.postalCode").value(organizerDTO.getAddress().getPostalCode()))
                .andExpect(jsonPath("$[0].address.country").value(organizerDTO.getAddress().getCountry()));
    }

    private AddressDTO createAddressDTO() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(1L);
        addressDTO.setStreet("Main Street 123");
        addressDTO.setCity("City");
        addressDTO.setPostalCode(011111);
        addressDTO.setCountry("Country");
        return addressDTO;
    }

    private OrganizerDTO createOrganizerDTO() {
        OrganizerDTO organizerDTO = new OrganizerDTO();
        organizerDTO.setId(1L);
        organizerDTO.setOrganizerName("Organizer");
        organizerDTO.setDescription("Description");
        organizerDTO.setWebsite("website.dns.com");
        organizerDTO.setPhoneNumber("0712345678");
        organizerDTO.setAddress(createAddressDTO());
        return organizerDTO;
    }
}
