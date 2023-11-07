package ro.itschool.ema.controllers;


import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.itschool.ema.models.dtos.ParticipantDTO;
import ro.itschool.ema.services.ParticipantService;

@RestController
@RequestMapping("/api")
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService){
        this.participantService = participantService;
    }

    @PostMapping("/events/{eventId}/participants")
    public ResponseEntity<ParticipantDTO> createParticipant(@RequestBody @Valid ParticipantDTO participantDTO) {
        return ResponseEntity.ok(participantService.createParticipant(participantDTO));
    }
}
