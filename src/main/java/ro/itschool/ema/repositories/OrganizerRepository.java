package ro.itschool.ema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.itschool.ema.models.entities.Organizer;

@Repository
public interface OrganizerRepository extends JpaRepository<Organizer, Long> {
    boolean existsByEmail(String email);
}
