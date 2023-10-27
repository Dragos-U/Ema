package ro.itschool.ema.models.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Data
@Validated
public class StaffDTO implements Serializable {
    private Long id;

    @NotEmpty(message = "firstName cannot be empty.")
    private String firstName;

    @NotEmpty(message = "lastName cannot be empty.")
    private String lastName;

    @NotNull(message = "dateOfBirth cannot be null.")
    @Past(message = "Invalid date.")
    private LocalDate dateOfBirth;

    @NotEmpty(message = "email cannot be empty.")
    @Email(message = "Invalid email. Please enter a valid email.")
    private String email;

    @NotEmpty(message = "phoneNumber cannot be empty.")
    private String phoneNumber;
}
