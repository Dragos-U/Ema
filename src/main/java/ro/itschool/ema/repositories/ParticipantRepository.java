package ro.itschool.ema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.itschool.ema.models.entities.Participant;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    boolean existsByName(String name);
    @Query("SELECT p FROM Participant p JOIN p.events e WHERE e.id = :eventId")
    List<Participant> findAllByEventId(@Param("eventId") Long eventId);
}
