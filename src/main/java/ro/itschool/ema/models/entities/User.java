package ro.itschool.ema.models.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@Validated
public abstract class User {
    @Column(name = "first_name")
    @NotNull(message = "Field cannot be null.")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "Field cannot be null.")
    private String lastName;

    @Column(name = "date_of_birth")
    @NotNull(message = "Field cannot be null.")
    @Past(message = "Invalid date.")
    private Date dateOfBirth;

    @Column(name = "email")
    @Email(message = "Invalid email. Please enter a valid email.")
    @NotNull(message = "Field cannot be null.")
    private String email;

    @Column(name = "phone_number")
    @NotNull(message = "Field cannot be null.")
    private String phoneNumber;
}
