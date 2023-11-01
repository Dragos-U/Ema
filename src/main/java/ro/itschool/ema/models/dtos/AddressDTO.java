package ro.itschool.ema.models.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Data
@Validated
public class AddressDTO implements Serializable {

    private Long id;

    @NotEmpty(message = "Address street field cannot be empty.")
    private String street;

    @NotEmpty(message = "Address city field cannot be empty.")
    private String city;

    @NotNull(message = "Address postal code field cannot be null.")
    private int postalCode;

    @NotEmpty(message = "Address country field cannot be empty.")
    private String country;
}
