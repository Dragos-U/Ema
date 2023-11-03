package ro.itschool.ema.services;

import ro.itschool.ema.models.dtos.OrganizerDTO;
import ro.itschool.ema.models.entities.Organizer;

import java.util.List;

public interface OrganizerService {
    OrganizerDTO createOrganizer(OrganizerDTO organizerDTO);
    List<OrganizerDTO> getAllOrganizers();
}
