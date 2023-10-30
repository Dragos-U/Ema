package ro.itschool.ema.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.itschool.ema.models.dtos.EventDTO;
import ro.itschool.ema.models.dtos.ParticipantDTO;
import ro.itschool.ema.models.dtos.StaffDTO;
import ro.itschool.ema.services.ParticipantService;
import ro.itschool.ema.services.StaffService;

@Validated
@RestController
@RequestMapping("/api")
public class StaffController {
    private final StaffService staffService;

    private final ParticipantService participantService;

    public StaffController(StaffService staffService, ParticipantService participantService) {
        this.staffService = staffService;
        this.participantService = participantService;
    }

    @PostMapping("/staff")
    public ResponseEntity<StaffDTO> createStaff(@RequestBody @Valid StaffDTO staffDTO) {
        return ResponseEntity.ok(staffService.createStaff(staffDTO));
    }

    @PostMapping("/events")
    public ResponseEntity<EventDTO> createEvent(@RequestBody @Valid EventDTO eventDTO) {
        return ResponseEntity.ok(staffService.createEvent(eventDTO));
    }

    @PostMapping("/events/{eventId}/participants")
    public ResponseEntity<ParticipantDTO> createParticipant(@RequestBody @Valid ParticipantDTO participantDTO) {
        return ResponseEntity.ok(participantService.createParticipant(participantDTO));
    }

}
