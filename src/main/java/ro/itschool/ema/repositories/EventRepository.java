package ro.itschool.ema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.itschool.ema.models.entities.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
