package ro.itschool.ema.models.dtos;

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

    @NotEmpty(message = "phoneNumber cannot be empty.")
    private String phoneNumber;

    @NotNull(message = "address cannot be empty.")
    private AddressDTO address;
}
