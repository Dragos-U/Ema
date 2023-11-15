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
import ro.itschool.ema.exceptions.OrganizerNotFoundException;
import ro.itschool.ema.exceptions.UserCreateException;
import ro.itschool.ema.models.dtos.AddressDTO;
import ro.itschool.ema.models.dtos.OrganizerDTO;
import ro.itschool.ema.models.dtos.StaffDTO;
import ro.itschool.ema.models.entities.Address;
import ro.itschool.ema.models.entities.Organizer;
import ro.itschool.ema.models.entities.Staff;
import ro.itschool.ema.repositories.AddressRepository;
import ro.itschool.ema.repositories.OrganizerRepository;
import ro.itschool.ema.repositories.StaffRepository;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class StaffServiceImplTest {

    @Mock
    private StaffRepository staffRepository;

    @Mock
    private OrganizerRepository organizerRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private StaffServiceImpl staffServiceImpl;

    @Test
    @DisplayName("Staff created successfully.")
    void createStaffTestShouldPass() {
        StaffDTO staffDTO = createStaffDTO();
        Organizer organizer = createOrganizer();
        Staff staffEntity = createStaffEntity();
        Address savedAddress = createAddressEntity();
        Staff savedStaff = createStaffEntity();
        OrganizerDTO organizerDTO = createOrganizerDTO();

        StaffServiceImpl spyService = Mockito.spy(staffServiceImpl);

        when(staffRepository.existsByEmail(staffDTO.getEmail())).thenReturn(false);
        when(objectMapper.convertValue(staffDTO, Staff.class)).thenReturn(staffEntity);
        when(organizerRepository.findById(staffDTO.getOrganizerId())).thenReturn(Optional.of(organizer));
        staffEntity.setOrganizer(organizer);

        when(addressRepository.save(staffEntity.getAddress())).thenReturn(savedAddress);
        doNothing().when(spyService).addToStaffList(organizer, staffEntity);
        when(staffRepository.save(staffEntity)).thenReturn(savedStaff);

        when(objectMapper.convertValue(savedStaff, StaffDTO.class)).thenReturn(staffDTO);
        when(objectMapper.convertValue(organizer, OrganizerDTO.class)).thenReturn(organizerDTO);

        StaffDTO result = spyService.createStaff(staffDTO);

        Assertions.assertEquals(staffDTO.getFirstName(), result.getFirstName());
        Assertions.assertEquals(staffDTO.getAddress(), result.getAddress());
        Assertions.assertEquals(staffDTO.getOrganizer(), result.getOrganizer());
    }

    @Test
    @DisplayName("Staff already exists.")
    void testUserAlreadyExistsShouldFail() {
        StaffDTO staffDTO = createStaffDTO();
        when(staffRepository.existsByEmail(staffDTO.getEmail())).thenReturn(true);
        Assertions.assertThrows(UserCreateException.class, () -> staffServiceImpl.createStaff(staffDTO));
    }

    @Test
    @DisplayName("Staff age is over age limit.")
    void testUserAgeIsOverLimitShouldFail() {
        StaffDTO staffDTO = createStaffDTO();
        staffDTO.setDateOfBirth(LocalDate.now().minusYears(15));
        Assertions.assertThrows(UserCreateException.class, () -> staffServiceImpl.createStaff(staffDTO));
    }

    @Test
    @DisplayName("Organizer entity not found.")
    void testOrganizerEntityNotFoundShouldFail() {
        StaffDTO staffDTO = createStaffDTO();
        when(organizerRepository.findById(staffDTO.getOrganizerId())).thenReturn(Optional.empty());
        Assertions.assertThrows(OrganizerNotFoundException.class, () -> staffServiceImpl.createStaff(staffDTO));
    }

    private StaffDTO createStaffDTO() {
        StaffDTO staffDTO = new StaffDTO();
        staffDTO.setId(1L);
        staffDTO.setFirstName("Jane");
        staffDTO.setLastName("Doe");
        staffDTO.setDateOfBirth(LocalDate.of(2000, 1,1));
        staffDTO.setEmail("jane@gmail.com");
        staffDTO.setPhoneNumber("0712345678");
        staffDTO.setAddress(createAddressDTO());
        staffDTO.setOrganizerId(1L);
        return staffDTO;
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

    private Address createAddressEntity() {
        Address addressEntity = new Address();
        addressEntity.setId(1L);
        addressEntity.setStreet("Main Street 123");
        addressEntity.setCity("City");
        addressEntity.setPostalCode(011111);
        addressEntity.setCountry("Country");
        return addressEntity;
    }

    private Organizer createOrganizer() {
        Organizer organizer = new Organizer();
        organizer.setId(1L);
        organizer.setOrganizerName("Organizer name");
        organizer.setStaffList(Collections.emptyList());
        return organizer;
    }

    private Staff createStaffEntity() {
        Staff staff = new Staff();
        staff.setId(1L);
        staff.setFirstName("Jane");
        staff.setLastName("Doe");
        staff.setDateOfBirth(LocalDate.of(2000, 1,1));
        staff.setEmail("jane@gmail.com");
        staff.setPhoneNumber("0712345678");
        staff.setAddress(createAddressEntity());
        staff.setOrganizer(createOrganizer());
        return staff;
    }

    private OrganizerDTO createOrganizerDTO() {
        OrganizerDTO organizerDTO = new OrganizerDTO();
        organizerDTO.setId(1L);
        organizerDTO.setOrganizerName("Organizer name");
        return organizerDTO;
    }
}

