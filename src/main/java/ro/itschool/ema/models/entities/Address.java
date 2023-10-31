package ro.itschool.ema.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "postal_code")
    private int postalCode;

    @Column(name = "country")
    private String country;

    @OneToOne(mappedBy = "address")
    private Organizer organizer;

    @OneToOne(mappedBy = "address")
    private Participant participant;

    @OneToOne(mappedBy = "address")
    private Staff staff;

}
