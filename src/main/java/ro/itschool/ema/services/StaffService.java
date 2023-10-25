package ro.itschool.ema.services;


import ro.itschool.ema.models.dtos.EventDTO;
import ro.itschool.ema.models.dtos.StaffDTO;

public interface StaffService {
    StaffDTO createStaff(StaffDTO staffDTO);
    EventDTO createEvent(EventDTO eventDTO);
}
