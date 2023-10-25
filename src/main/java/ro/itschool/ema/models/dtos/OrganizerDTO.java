package ro.itschool.ema.models.dtos;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class OrganizerDTO implements Serializable {
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String email;
    private String phoneNumber;
}
