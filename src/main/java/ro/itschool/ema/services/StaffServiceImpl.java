package ro.itschool.ema.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.itschool.ema.exceptions.OrganizerNotFoundException;
import ro.itschool.ema.exceptions.UserCreateException;
import ro.itschool.ema.models.dtos.OrganizerDTO;
import ro.itschool.ema.models.dtos.StaffDTO;
import ro.itschool.ema.models.entities.Address;
import ro.itschool.ema.models.entities.Organizer;
import ro.itschool.ema.models.entities.Staff;
import ro.itschool.ema.repositories.AddressRepository;
import ro.itschool.ema.repositories.OrganizerRepository;
import ro.itschool.ema.repositories.StaffRepository;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final AddressRepository addressRepository;
    private final OrganizerRepository organizerRepository;
    private final ObjectMapper objectMapper;


    @Override
    @Transactional
    public StaffDTO createStaff(StaffDTO staffDTO) {

        if (staffRepository.existsByEmail(staffDTO.getEmail())) {
            throw new UserCreateException("User already exists");
        }

        if (getAge(staffDTO.getDateOfBirth()) < 16) {
            throw new UserCreateException("User must be at least 16.");
        }

        Staff staffEntity = objectMapper.convertValue(staffDTO, Staff.class);

        // identify organizer by id, throws exception if organizer is not identified
        Organizer organizer = getOrganizer(staffDTO.getOrganizerId());
        staffEntity.setOrganizer(organizer);

        // add staff to the organizer staff list
        addToStaffList(organizer, staffEntity);

        if (staffEntity.getAddress() != null) {
            Address addressEntity = addressRepository.save(staffEntity.getAddress());
            staffEntity.setAddress(addressEntity);
        }

        Staff staffResponseEntity = staffRepository.save(staffEntity);

        OrganizerDTO organizerDTOResponse = objectMapper.convertValue(organizer, OrganizerDTO.class);
        StaffDTO staffDTOResponse = objectMapper.convertValue(staffResponseEntity, StaffDTO.class);

        staffDTOResponse.setOrganizerId(organizerDTOResponse.getId());
        staffDTOResponse.setOrganizer(organizerDTOResponse);

        return staffDTOResponse;
    }

    @Override
    public List<StaffDTO> findByOrganizer(Organizer organizer) {
        return null;
    }

    private int getAge(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now();
        return Period.between(dateOfBirth, today).getYears();
    }

    private Organizer getOrganizer(Long organizerId){
        return organizerRepository.findById(organizerId)
                .orElseThrow(() -> new OrganizerNotFoundException("Could not find specified organization"));
    }

    private void addToStaffList(Organizer organizer, Staff staff){
        List<Staff> staffList = organizer.getStaffList();
        staffList.add(staff);
        organizerRepository.save(organizer);
    }
}
