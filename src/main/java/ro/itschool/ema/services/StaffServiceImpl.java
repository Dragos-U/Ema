package ro.itschool.ema.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ro.itschool.ema.exceptions.EventCreateException;
import ro.itschool.ema.exceptions.UserCreateException;
import ro.itschool.ema.models.dtos.EventDTO;
import ro.itschool.ema.models.dtos.StaffDTO;
import ro.itschool.ema.models.entities.Event;
import ro.itschool.ema.models.entities.Staff;
import ro.itschool.ema.repositories.EventRepository;
import ro.itschool.ema.repositories.StaffRepository;

import java.util.Set;

@Slf4j
@Service
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepository;
    private final EventRepository eventRepository;
    private final ObjectMapper objectMapper;

    public StaffServiceImpl(StaffRepository staffRepository, EventRepository eventRepository, ObjectMapper objectMapper) {
        this.staffRepository = staffRepository;
        this.eventRepository = eventRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public StaffDTO createStaff(StaffDTO staffDTO) {
        Staff staffEntity = objectMapper.convertValue(staffDTO, Staff.class);

        if (staffRepository.existsByEmail(staffEntity.getEmail())) {
            throw new UserCreateException("User already exists");
        }
        if (staffEntity.getAge(staffEntity.getDateOfBirth()) < 16) {
            throw new UserCreateException("User must be at least 16.");
        }
        Staff staffResponseEntity = staffRepository.save(staffEntity);
        return objectMapper.convertValue(staffResponseEntity, StaffDTO.class);
    }

    @Override
    public EventDTO createEvent(EventDTO eventDTO) {
        Event eventEntity = objectMapper.convertValue(eventDTO, Event.class);

        if (!eventDTO.getStartTime().isBefore(eventDTO.getEndTime())) {
            throw new EventCreateException("Event start time must be before event end time.");
        }

        Event eventResponseEntity = eventRepository.save(eventEntity);
        return objectMapper.convertValue(eventResponseEntity, EventDTO.class);
    }
}
