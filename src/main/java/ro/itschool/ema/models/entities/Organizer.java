package ro.itschool.ema.models.entities;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "organizers")
public class Organizer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "organizer_name", unique = true)
    private String organizerName;
    @Column(name = "description")
    private String description;
    @Column(name = "website")
    private String website;
    @OneToMany(mappedBy = "organizer")
    private List<Staff> staffList = new ArrayList<>();

    @OneToMany(mappedBy = "organizer")
    private List<Event> eventList = new ArrayList<>();

    @OneToOne(mappedBy = "organizer")
    private Address address;

    public void addStaffToList(Staff staff) {
        staffList.add(staff);
    }
}


