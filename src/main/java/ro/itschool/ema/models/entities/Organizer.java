package ro.itschool.ema.models.entities;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "organizers")
public class Organizer extends User{
    @Id
    private Long id;
    @Builder
    public Organizer(String firstName, String lastName, Date dateOfBirth, String email, String phoneNumber) {
        super(firstName, lastName, dateOfBirth, email, phoneNumber);
    }
}
