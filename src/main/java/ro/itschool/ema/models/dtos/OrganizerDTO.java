package ro.itschool.ema.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@Validated
public class OrganizerDTO implements Serializable {

    private Long id;

    @NotEmpty(message = "Organizer name field cannot be empty.")
    private String organizerName;

    @NotEmpty(message = "Description field cannot be empty.")
    private String description;

    @NotEmpty(message = "Website field cannot be empty.")
    @URL(message = "Must be a valid website.")
    private String website;

    @NotEmpty(message = "Phone number field cannot be empty.")
    private String phoneNumber;

    @NotNull(message = "Address field cannot be null")
    private AddressDTO address;
}
