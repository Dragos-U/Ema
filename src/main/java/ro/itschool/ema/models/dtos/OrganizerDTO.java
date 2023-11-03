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

    @NotEmpty(message = "organizerName cannot be empty.")
    private String organizerName;

    @NotEmpty(message = "description cannot be empty.")
    private String description;

    @NotEmpty(message = "website cannot be empty.")
    @URL(message = "Must be a valid website.")
    private String website;

    @NotEmpty(message = "phoneNumber cannot be empty.")
    private String phoneNumber;
}
