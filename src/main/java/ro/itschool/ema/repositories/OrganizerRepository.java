package ro.itschool.ema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.itschool.ema.models.entities.Organizer;
import ro.itschool.ema.models.entities.Staff;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
    boolean existsByOrganizerName(String organizerName);
    boolean existsByWebsite(String website);
}
