package ro.itschool.ema.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.itschool.ema.exceptions.UserCreateException;
import ro.itschool.ema.models.dtos.StaffDTO;
import ro.itschool.ema.models.entities.Address;
import ro.itschool.ema.models.entities.Staff;
import ro.itschool.ema.repositories.AddressRepository;
import ro.itschool.ema.repositories.StaffRepository;

import java.time.LocalDate;
import java.time.Period;

@Slf4j
@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final AddressRepository addressRepository;
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

        if (staffEntity.getAddress() != null) {
            Address addressEntity = addressRepository.save(staffEntity.getAddress());
            staffEntity.setAddress(addressEntity);
        }

        Staff staffResponseEntity = staffRepository.save(staffEntity);
        return objectMapper.convertValue(staffResponseEntity, StaffDTO.class);
    }

    private int getAge(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now();
        return Period.between(dateOfBirth, today).getYears();
    }
}
