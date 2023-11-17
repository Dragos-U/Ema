package ro.itschool.ema.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.itschool.ema.models.dtos.ParticipantDTO;
import ro.itschool.ema.services.ParticipantService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService){
        this.participantService = participantService;
    }

    @PostMapping("/events/{eventId}/participants")
    public ResponseEntity<ParticipantDTO> addParticipantToEvent(@PathVariable Long eventId, @RequestBody @Valid ParticipantDTO participantDTO) {
        ParticipantDTO savedParticipant = participantService.createAndAddParticipantToEvent(eventId, participantDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedParticipant);
    }
    @GetMapping("/events/{eventId}/participants")
    public ResponseEntity<List<ParticipantDTO>> getParticipantsForEvent(@PathVariable Long eventId) {
        List<ParticipantDTO> participants = participantService.getParticipantsForEvent(eventId);
        return ResponseEntity.ok(participants);
    }
}