package ro.itschool.ema.controllers;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.itschool.ema.models.dtos.OrganizerDTO;
import ro.itschool.ema.services.OrganizerService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OrganizerController {

    private final OrganizerService organizerService;

    public OrganizerController(OrganizerService organizerService) {
        this.organizerService = organizerService;
    }

    @GetMapping("/organizers")
    public ResponseEntity<List<OrganizerDTO>> getAllOrganizers() {
        return ResponseEntity.ok(organizerService.getAllOrganizers());
    }
    @PostMapping("/organizers")
    public ResponseEntity<OrganizerDTO> createOrganizer(@RequestBody @Valid OrganizerDTO organizerDTO) {
        return ResponseEntity.ok(organizerService.createOrganizer(organizerDTO));
    }

}
