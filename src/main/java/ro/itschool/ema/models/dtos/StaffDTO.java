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

    @NotEmpty(message = "Field cannot be empty.")
    private String firstName;

    @NotEmpty(message = "Field cannot be empty.")
    private String lastName;

    @NotNull(message = "Field cannot be null.")
    @Past(message = "Invalid date.")
    private LocalDate dateOfBirth;

    @NotEmpty(message = "Field cannot be empty.")
    @Email(message = "Invalid email. Please enter a valid email.")
    private String email;

    @NotEmpty(message = "Field cannot be empty.")
    private String phoneNumber;
}
