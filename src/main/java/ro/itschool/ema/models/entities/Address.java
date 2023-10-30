package ro.itschool.ema.models.entities;

import lombok.Data;
import jakarta.persistence.*;
@Data
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer")
    private Organizer organizer;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant")
    private Participant participant;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staff")
    private Staff staff;

}
