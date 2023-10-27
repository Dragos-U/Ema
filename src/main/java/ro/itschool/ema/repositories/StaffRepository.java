package ro.itschool.ema.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ro.itschool.ema.models.entities.Staff;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    boolean existsByEmail(String email);
}
