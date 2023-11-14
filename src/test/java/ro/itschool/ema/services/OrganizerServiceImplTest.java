package ro.itschool.ema.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ro.itschool.ema.exceptions.OrganizerCreateException;
import ro.itschool.ema.models.dtos.AddressDTO;
import ro.itschool.ema.models.dtos.OrganizerDTO;
import ro.itschool.ema.models.entities.Address;
import ro.itschool.ema.models.entities.Organizer;
import ro.itschool.ema.repositories.AddressRepository;
import ro.itschool.ema.repositories.OrganizerRepository;

@ExtendWith(MockitoExtension.class)
class OrganizerServiceImplTest {

    @Mock
    private OrganizerRepository organizerRepository;
    @Mock
    private AddressRepository addressRepository;
    @Mock
    private ObjectMapper objectMapper;
    @InjectMocks
    private OrganizerServiceImpl organizerServiceImpl;

    @Test
    @DisplayName("Organizer created successfully.")
    void createOrganizerTestShouldPass() {
        OrganizerDTO organizerDTO = createOrganizerDTO();
        Organizer organizerEntity = createOrganizerEntity();
        Organizer savedOrganizer = createSavedOrganizerEntity();
        Address savedAddress = createAddressEntity();

        OrganizerServiceImpl spyService = Mockito.spy(organizerServiceImpl);

        Mockito.when(organizerRepository.existsByOrganizerName(organizerDTO.getOrganizerName())).thenReturn(false);
        Mockito.when(objectMapper.convertValue(organizerDTO, Organizer.class)).thenReturn(organizerEntity);
        organizerEntity.setAddress(savedAddress);

        Mockito.when(organizerRepository.save(organizerEntity)).thenReturn(savedOrganizer);
        Mockito.when(objectMapper.convertValue(savedOrganizer, OrganizerDTO.class)).thenReturn(organizerDTO);

        OrganizerDTO result = spyService.createOrganizer(organizerDTO);

        Assertions.assertEquals(organizerDTO.getOrganizerName(), result.getOrganizerName());
    }

    @Test
    @DisplayName("Throws exception if organizer is already in the list.")
    void organizerExistsTestThrowsException() {
        OrganizerDTO organizerDTO = createOrganizerDTO();

        Mockito.when(organizerRepository.existsByWebsite(organizerDTO.getWebsite())).thenReturn(true);
        Assertions.assertThrows(OrganizerCreateException.class, ()-> organizerServiceImpl.createOrganizer(organizerDTO));
    }

    private OrganizerDTO createOrganizerDTO() {
        OrganizerDTO organizerDTO = new OrganizerDTO();
        organizerDTO.setId(1L);
        organizerDTO.setOrganizerName("Organizer");
        organizerDTO.setDescription("Description");
        organizerDTO.setWebsite("website@dns.com");
        organizerDTO.setPhoneNumber("0712345678");
        organizerDTO.setAddress(createAddressDTO());
        return organizerDTO;
    }

    private Organizer createOrganizerEntity() {
        Organizer organizer = new Organizer();
        organizer.setId(1L);
        organizer.setOrganizerName("Organizer");
        organizer.setDescription("Description");
        organizer.setWebsite("website@dns.com");
        organizer.setPhoneNumber("0712345678");
        return organizer;
    }

    private Organizer createSavedOrganizerEntity() {
        Organizer organizer = new Organizer();
        organizer.setId(1L);
        organizer.setOrganizerName("Organizer");
        organizer.setDescription("Description");
        organizer.setWebsite("website@dns.com");
        organizer.setPhoneNumber("0712345678");
        organizer.setAddress(createAddressEntity());
        return organizer;
    }

    private AddressDTO createAddressDTO() {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(1L);
        addressDTO.setStreet("Main Street 123");
        addressDTO.setCity("City");
        addressDTO.setPostalCode(4681);
        addressDTO.setCountry("Country");
        return addressDTO;
    }

    private Address createAddressEntity() {
        Address addressEntity = new Address();
        addressEntity.setId(1L);
        addressEntity.setStreet("Main Street 123");
        addressEntity.setCity("City");
        addressEntity.setPostalCode(4681);
        addressEntity.setCountry("Country");
        return addressEntity;
    }
}
