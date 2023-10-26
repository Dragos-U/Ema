package ro.itschool.ema.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.Date;

@Data
@Validated
public class OrganizerDTO implements Serializable {
    private Long id;

    @NotEmpty(message = "Field cannot be empty.")
    private String entityName;

    @NotEmpty(message = "Field cannot be empty.")
    private String description;

    @NotEmpty(message = "Field cannot be empty.")
    @URL(message = "Must be a valid website.")
    private String website;

    @NotEmpty(message = "Field cannot be empty.")
    private String phoneNumber;
}
