package ro.itschool.ema.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

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

        Set<ConstraintViolation<Staff>> violations = validator.validate(staffEntity);
        for (ConstraintViolation<Staff> vio : violations) {
            log.error(vio.getMessage());
        }

        if (staffRepository.existsByEmail(staffEntity.getEmail())) {
            throw new UserCreateException("User already exists");
        }
        Staff staffResponseEntity = staffRepository.save(staffEntity);
        return objectMapper.convertValue(staffResponseEntity, StaffDTO.class);
    }

    @Override
    public EventDTO createEvent(EventDTO eventDTO) {
        Event eventEntity = objectMapper.convertValue(eventDTO, Event.class);
        Set<ConstraintViolation<Event>> violations = validator.validate(eventEntity);

        for (ConstraintViolation<Event> vio : violations) {
            log.error(vio.getMessage());
        }

        Event eventResponseEntity = eventRepository.save(eventEntity);
        return objectMapper.convertValue(eventResponseEntity, EventDTO.class);
    }
}
