package ro.itschool.ema.models.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "admin_staff")
public class Staff extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Builder
    public Staff(String firstName, String lastName, LocalDate dateOfBirth, String email, String phoneNumber) {
        super(firstName, lastName, dateOfBirth, email, phoneNumber);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer")
    private Organizer organizer;

    public int getAge(LocalDate dateOfBirth) {
        LocalDate today = LocalDate.now();
        return Period.between(dateOfBirth, today).getYears();
    }
}
