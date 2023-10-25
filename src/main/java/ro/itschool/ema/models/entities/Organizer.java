package ro.itschool.ema.models.entities;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EqualsAndHashCode(callSuper = true)
@Entity
@Validated
@Table(name = "organizers")
public class Organizer extends User {
    @Id
    private Long id;
    @Builder
    public Organizer(String firstName, String lastName, Date dateOfBirth, String email, String phoneNumber) {
        super(firstName, lastName, dateOfBirth, email, phoneNumber);
    }
}
