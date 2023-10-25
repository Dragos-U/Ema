package ro.itschool.ema.models.entities;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Validated
@Table(name = "admin_staff")
public class Staff extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Builder
    public Staff(String firstName, String lastName, Date dateOfBirth, String email, String phoneNumber) {
        super(firstName, lastName, dateOfBirth, email, phoneNumber);
    }
}
