package ro.itschool.ema.models.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@Validated
public class ParticipantDTO implements Serializable {
    private Long id;

    @NotEmpty(message = "Participant name field cannot be empty")
    private String name;

    @NotEmpty(message = "Participant email field cannot be empty")
    private String email;

    @NotEmpty(message = "Phone number field cannot be empty.")
    private String phoneNumber;

    @NotNull(message = "Address field cannot be null.")
    @Valid
    private AddressDTO address;
}
