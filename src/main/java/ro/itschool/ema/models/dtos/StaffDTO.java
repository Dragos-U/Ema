package ro.itschool.ema.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.Date;

@Data
@Validated
public class StaffDTO implements Serializable {
    private Long id;

    @NotNull(message = "Field cannot be null.")
    private String firstName;

    @NotNull(message = "Field cannot be null.")
    private String lastName;

    @NotNull(message = "Field cannot be null.")
    @Past(message = "Invalid date.")
    private Date dateOfBirth;

    @NotNull(message = "Field cannot be null.")
    @Email(message = "Invalid email. Please enter a valid email.")
    private String email;

    @NotNull(message = "Field cannot be null.")
    private String phoneNumber;
}
