package ro.itschool.ema.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
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
        List<OrganizerDTO> allOrganizers = organizerService.getAllOrganizers();
        if (allOrganizers.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(allOrganizers);
        }
    }
    @PostMapping("/organizers")
    public ResponseEntity<OrganizerDTO> createOrganizer(@RequestBody @Valid OrganizerDTO organizerDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(organizerService.createOrganizer(organizerDTO));
    }
}
