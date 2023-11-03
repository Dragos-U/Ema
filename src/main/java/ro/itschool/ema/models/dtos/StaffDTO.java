package ro.itschool.ema.models.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Validated
public class StaffDTO implements Serializable {
    private Long id;

    @NotEmpty(message = "First name field cannot be empty.")
    private String firstName;

    @NotEmpty(message = "Last name field  cannot be empty.")
    private String lastName;

    @NotNull(message = "Date of birth field cannot be null.")
    @Past(message = "Invalid date.")
    private LocalDate dateOfBirth;

    @NotEmpty(message = "Email field cannot be empty.")
    @Email(message = "Invalid email. Please enter a valid email.")
    private String email;

    @NotEmpty(message = "Phone number field cannot be empty.")
    private String phoneNumber;

    @NotNull(message = "Address field cannot be null.")
    private AddressDTO address;

    @NotNull(message = "Organizer id cannot be null")
    private Long organizerId;

    private OrganizerDTO organizer;
}
