package ro.itschool.ema.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.extension.ExtendWith;
import ro.itschool.ema.models.entities.Event;
import ro.itschool.ema.models.entities.Participant;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class ParticipantRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ParticipantRepository participantRepository;

    @Test
    @DisplayName("Participant exists by name")
    void whenExistsByName_thenReturnTrue() {

        Participant participant = new Participant();
        participant.setName("John Doe");
        participant.setEmail("john.doe@example.com");
        entityManager.persist(participant);
        entityManager.flush();

        boolean exists = participantRepository.existsByName(participant.getName());

        assertThat(exists).isTrue();
    }

    @Test
    @DisplayName("Participants found by Event ID")
    void whenFindAllByEventId_thenReturnParticipants() {
        Event event = new Event();
        event.setEventName("Event Name");
        event = entityManager.persistFlushFind(event);

        Participant participant1 = new Participant();
        participant1.setName("John Doe");
        participant1.setEmail("john.doe@example.com");
        participant1.setEvents(new HashSet<>(Arrays.asList(event)));

        Participant participant2 = new Participant();
        participant2.setName("Jane Doe");
        participant2.setEmail("jane.doe@example.com");
        participant2.setEvents(new HashSet<>(Arrays.asList(event)));

        event.getParticipants().add(participant1);
        event.getParticipants().add(participant2);

        entityManager.persist(participant1);
        entityManager.persist(participant2);
        entityManager.flush();

        List<Participant> foundParticipants = participantRepository.findAllByEventId(event.getId());

        assertThat(foundParticipants).hasSize(2);
        assertThat(foundParticipants).extracting(Participant::getName).containsExactlyInAnyOrder("John Doe", "Jane Doe");
    }
}
