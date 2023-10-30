package ro.itschool.ema.models.entities;

import lombok.Data;
import jakarta.persistence.*;
@Data
@Entity
@Table(name = "participants")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "participant_name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private int phoneNumber;

    @OneToOne(mappedBy = "participant")
    private Address address;

}
