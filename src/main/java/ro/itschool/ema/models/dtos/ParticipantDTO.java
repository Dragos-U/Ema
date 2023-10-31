package ro.itschool.ema.models.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    @NotNull(message = "Participant must have a phone number")
    @Min(value = 7, message = "Participant phone number minimum length has to be greater or equal to 7 ")
    @Max(value = 15, message = "Participant phone number maximum length has to be less or equal to 15")
    private int phoneNumber;

}
