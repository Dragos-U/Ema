package ro.itschool.ema.models.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "organizers")
public class Organizer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "entity_name", unique = true)
    private String entityName;
    @Column(name = "description")
    private String description;
    @Column(name = "website")
    private String website;
}


