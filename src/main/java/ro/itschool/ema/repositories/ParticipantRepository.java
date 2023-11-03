package ro.itschool.ema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.itschool.ema.models.entities.Participant;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    boolean existsByName(String name);
    boolean existsByEmail(String email);

}
