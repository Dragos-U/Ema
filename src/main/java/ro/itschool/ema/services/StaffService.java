package ro.itschool.ema.services;


import ro.itschool.ema.models.dtos.StaffDTO;
import ro.itschool.ema.models.entities.Organizer;

import java.util.List;

public interface StaffService {
    StaffDTO createStaff(StaffDTO staffDTO);
    List<StaffDTO> findByOrganizer(Organizer organizer);
}
